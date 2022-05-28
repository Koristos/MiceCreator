package ru.geekbrains.micecreator.service.files;

import lombok.AllArgsConstructor;
import lombok.Data;
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
@Data
public class FileService {

	private final Path root = Paths.get("C:/MiceCreator");
	private final Path uploads = Paths.get(root.toString(), "/uploads");
	private final Path images = Paths.get(root.toString(), "/images");
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
			if (!Files.exists(root)){
				Files.createDirectory(root);
			}
			if (!Files.exists(images)) {
				Files.createDirectory(images);
			}
			if (!Files.exists(uploads)) {
				Files.createDirectory(uploads);
			}
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	public boolean saveImage(MultipartFile file, String imageId) {
		Path path = this.images.resolve(imageId);
		if (Files.exists(path)){
			FileSystemUtils.deleteRecursively(path.toFile());
		}
		try {
			Files.copy(file.getInputStream(), path);
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
		return true;
	}

	public Resource getImage(String imageId) {
		Path file = images.resolve(imageId);
		return getFile(file);
	}

	public Resource loadEstimate(Integer tourId) {
		Path file = uploads.resolve(ESTIMATE_TEMP);
		estimateCreator.createEstimate(makeEstimate(tourId), file.toString());
		return getFile(file);
	}

	public void clearUploads() {
		FileSystemUtils.deleteRecursively(uploads.toFile());
	}

	public boolean deleteImage(String imageName) {
		Path path = this.images.resolve(imageName);
		if (Files.exists(path)){
			FileSystemUtils.deleteRecursively(path.toFile());
		}
		return true;
	}

	private TourEstimate makeEstimate(Integer tourId) {
		TourEstimate estimate = new TourEstimate();
		estimate.setTour(tourService.findDtoById(tourId));
		estimate.setAccommodations(accommodationService.makeEstimate(tourId));
		estimate.setFlights(flightService.makeEstimate(tourId));
		estimate.setHotelEvents(hotelEventService.makeEstimate(tourId));
		estimate.setRegionEvents(regionEventService.makeEstimate(tourId));
		return estimate;
	}

	private Resource getFile(Path path) {
		try {
			Resource resource = new UrlResource(path.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}
	}

}
