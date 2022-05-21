package ru.geekbrains.micecreator.service.files;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.micecreator.dto.complex.estimate.TourEstimate;
import ru.geekbrains.micecreator.service.AccommodationService;
import ru.geekbrains.micecreator.service.FlightService;
import ru.geekbrains.micecreator.service.HotelEventService;
import ru.geekbrains.micecreator.service.RegionEventService;
import ru.geekbrains.micecreator.service.TourService;
import ru.geekbrains.micecreator.transformers.xls.EstimateCreator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class FileService {

	private final Path root = Paths.get("C:/MiceCreatorUploads");
	public final static String ESTIMATE_TEMP = "estimate.xls";
	@Autowired
	private final EstimateCreator estimateCreator;
	@Autowired
	private final TourService tourService;
	@Autowired
	private final AccommodationService accommodationService;
	@Autowired
	private final FlightService flightService;
	@Autowired
	private final RegionEventService regionEventService;
	@Autowired
	private final HotelEventService hotelEventService;

	public void init() {
		try {
			Files.createDirectory(root);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}
	public void save(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}
	public Resource loadEstimate(Integer tourId) {
		estimateCreator.createEstimate(makeEstimate(tourId), root.resolve(ESTIMATE_TEMP).toString());
		try {
			Path file = root.resolve(ESTIMATE_TEMP);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}

	private TourEstimate makeEstimate (Integer tourId) {
		TourEstimate estimate = new TourEstimate();
		estimate.setTour(tourService.findDtoById(tourId));
		estimate.setAccommodations(accommodationService.makeEstimate(tourId));
		estimate.setFlights(flightService.makeEstimate(tourId));
		estimate.setHotelEvents(hotelEventService.makeEstimate(tourId));
		estimate.setRegionEvents(regionEventService.makeEstimate(tourId));
		return estimate;
	}

}
