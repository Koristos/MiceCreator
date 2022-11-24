package ru.geekbrains.micecreator.service.files;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.micecreator.dto.basic.full.HotelDto;
import ru.geekbrains.micecreator.dto.basic.full.HotelServiceDto;
import ru.geekbrains.micecreator.dto.basic.full.LocationDto;
import ru.geekbrains.micecreator.dto.basic.full.RegionServiceDto;
import ru.geekbrains.micecreator.dto.basic.full.RoomDto;
import ru.geekbrains.micecreator.dto.complex.AccommodationDto;
import ru.geekbrains.micecreator.dto.complex.HotelEventDto;
import ru.geekbrains.micecreator.dto.complex.RegionEventDto;
import ru.geekbrains.micecreator.dto.complex.TourDto;
import ru.geekbrains.micecreator.dto.complex.estimate.TourEstimate;
import ru.geekbrains.micecreator.dto.complex.presentation.AccHotel;
import ru.geekbrains.micecreator.dto.complex.presentation.AccLocation;
import ru.geekbrains.micecreator.dto.complex.presentation.AccRoom;
import ru.geekbrains.micecreator.dto.complex.presentation.AirlineDesc;
import ru.geekbrains.micecreator.dto.complex.presentation.EventDesc;
import ru.geekbrains.micecreator.dto.complex.presentation.EventPlace;
import ru.geekbrains.micecreator.dto.complex.presentation.TourPresentation;
import ru.geekbrains.micecreator.exceptions.FileProcessException;
import ru.geekbrains.micecreator.service.AccommodationService;
import ru.geekbrains.micecreator.service.AirlineService;
import ru.geekbrains.micecreator.service.FlightService;
import ru.geekbrains.micecreator.service.HotelEventService;
import ru.geekbrains.micecreator.service.HotelServService;
import ru.geekbrains.micecreator.service.HotelService;
import ru.geekbrains.micecreator.service.LocationService;
import ru.geekbrains.micecreator.service.RegionEventService;
import ru.geekbrains.micecreator.service.RegionServService;
import ru.geekbrains.micecreator.service.RegionService;
import ru.geekbrains.micecreator.service.RoomService;
import ru.geekbrains.micecreator.service.TourService;
import ru.geekbrains.micecreator.transformers.pdf.PresentationCreator;
import ru.geekbrains.micecreator.transformers.xls.EstimateCreator;
import ru.geekbrains.micecreator.utils.AppUtils;
import ru.geekbrains.micecreator.utils.PathUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
@Data
public class FileService {

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	public final static String ESTIMATE_TEMP = "estimate.xls";
	public final static String PRESENTATION_TEMP = "presentation.pdf";

	@Autowired
	private final PathUtils pathUtils;
	@Autowired
	private final EstimateCreator estimateCreator;
	@Autowired
	private final PresentationCreator presentationCreator;
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
	@Autowired
	private final AirlineService airlineService;
	@Autowired
	private final HotelService hotelService;
	@Autowired
	private final LocationService locationService;
	@Autowired
	private final RegionService regionService;
	@Autowired
	private final RoomService roomService;
	@Autowired
	private final HotelServService hotelServService;
	@Autowired
	private final RegionServService regionServService;


	public void init() {
		try {
			if (!Files.exists(pathUtils.getRootPath())) {
				Files.createDirectory(pathUtils.getRootPath());
			}
			if (!Files.exists(pathUtils.getImagesPath())) {
				Files.createDirectory(pathUtils.getImagesPath());
			}
			if (!Files.exists(pathUtils.getUploadsPath())) {
				Files.createDirectory(pathUtils.getUploadsPath());
			}
		} catch (IOException e) {
			throw new FileProcessException("Could not initialize folder for upload!");
		}
	}

	/**
	 * Сохраняет изображение в папке для хранения изображений
	 * @param file изображение
	 * @param imageId имя изображения
	 * @return true, если все прошло хорошо
	 */
	public boolean saveImage(MultipartFile file, String imageId) {
		Path path = this.pathUtils.getImagesPath().resolve(imageId);
		if (Files.exists(path)) {
			FileSystemUtils.deleteRecursively(path.toFile());
		}
		try {
			Files.copy(file.getInputStream(), path);
		} catch (Exception e) {
			throw new FileProcessException("Could not store the file. Error: " + e.getMessage());
		}
		return true;
	}

	/**
	 * Возвращает имеющиееся в наличии изображение
	 * @param imageId имя файла
	 * @return файл с картинкой, если есть в наличии
	 */
	public Resource getImage(String imageId) {
		Path file = pathUtils.getImagesPath().resolve(imageId);
		return getFile(file);
	}

	/**
	 * Создает смету в формате XLS по параметрам тура
	 * @param tourId id тура, для которого создается смета
	 * @return файл со сметой
	 */
	public Resource loadEstimate(Integer tourId) {
		Path file = pathUtils.getUploadsPath().resolve(ESTIMATE_TEMP);
		estimateCreator.createEstimate(makeEstimate(tourId), file.toString());
		return getFile(file);
	}

	/**
	 * Создает презентацию в формате PDF по параметрам тура
	 * @param tourId id тура, для которого создается презентация
	 * @return файл со презентацией
	 */
	public Resource loadPresentation(Integer tourId) {
		Path file = pathUtils.getUploadsPath().resolve(PRESENTATION_TEMP);
		presentationCreator.createPresentation(makePresentation(tourId), file.toString());
		return getFile(file);
	}

	/**
	 * Метод очищает папку, содержащую сформированные файлалы
	 */
	public void clearUploads() {
		FileSystemUtils.deleteRecursively(pathUtils.getUploadsPath().toFile());
	}

	/**
	 * Метод удаляет изображение по названию файла
	 * @param imageName название файла
	 * @return true, если все прошло хорошо
	 */
	public boolean deleteImage(String imageName) {
		Path path = this.pathUtils.getImagesPath().resolve(imageName);
		if (Files.exists(path)) {
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
				throw new FileProcessException("Файл не найден или поврежден.");
			}
		} catch (MalformedURLException e) {
			throw new FileProcessException("Error: " + e.getMessage());
		}
	}

	private TourPresentation makePresentation(Integer tourId) {
		TourPresentation presentation = new TourPresentation();
		TourDto tour = tourService.findDtoById(tourId);
		presentation.setCountry(tour.getCountry().getName());
		presentation.setStartDate(formatter.format(tour.getStartDate()));
		presentation.setEndDate(formatter.format(tour.getEndDate()));

		flightService.findDtoByTourId(tourId).forEach(flight -> {
			AirlineDesc airline = new AirlineDesc();
			airline.setName(flight.getAirline().getName());
			airline.setDesc(airlineService.findDtoById(flight.getAirline().getId()).getDescription());
			if (!presentation.getAirlines().contains(airline)) {
				presentation.getAirlines().add(airline);
			}
		});

		List<AccommodationDto> accommodations = accommodationService.findDtoByTourId(tourId);
		for (AccommodationDto accom : accommodations) {
			RoomDto room = roomService.findDtoById(accom.getRoom().getId());
			HotelDto hotel = hotelService.findDtoById(accom.getHotel().getId());
			LocationDto location = locationService.findDtoById(hotel.getLocationId());

			AccRoom roomDesc = new AccRoom(room);
			roomDesc.setImageOneLink(convertImageIdToLink(room.getImageOne()));
			roomDesc.setImageTwoLink(convertImageIdToLink(room.getImageTwo()));

			AccHotel hotelDesc = new AccHotel(hotel);
			hotelDesc.setImageOneLink(convertImageIdToLink(hotel.getImageOne()));
			hotelDesc.setImageTwoLink(convertImageIdToLink(hotel.getImageTwo()));

			AccLocation locationDesc = new AccLocation(location);
			locationDesc.setImageLink(convertImageIdToLink(location.getImageOne()));

			AccLocation validLocation;
			if (!presentation.getAccLocations().contains(locationDesc)) {
				presentation.getAccLocations().add(locationDesc);
				validLocation = locationDesc;
			} else {
				validLocation = presentation.getAccLocations().stream()
						.filter(loc -> loc.equals(locationDesc)).findFirst().get();
			}

			AccHotel validHotel;
			if (!validLocation.getAccHotels().contains(hotelDesc)) {
				validLocation.getAccHotels().add(hotelDesc);
				validHotel = hotelDesc;
			} else {
				validHotel = validLocation.getAccHotels().stream()
						.filter(loc -> loc.equals(hotelDesc)).findFirst().get();
			}

			if (!validHotel.getAccRooms().contains(roomDesc)) {
				validHotel.getAccRooms().add(roomDesc);
			}
		}

		List<HotelEventDto> hotelEvents = hotelEventService.findDtoByTourId(tourId);
		hotelEvents.forEach(event -> {
			HotelServiceDto service = hotelServService.findDtoById(event.getService().getId());
			EventPlace place = new EventPlace(hotelService.findDtoById(service.getHotelId()));
			EventDesc eventDesc = new EventDesc(service);
			eventDesc.setImageOneLink(convertImageIdToLink(service.getImageOne()));
			eventDesc.setImageTwoLink(convertImageIdToLink(service.getImageTwo()));
			EventPlace validPlace = new EventPlace();
			if (!presentation.getEventHotels().contains(place)) {
				presentation.getEventHotels().add(place);
				validPlace = place;
			} else {
				validPlace = presentation.getEventHotels().stream()
						.filter(loc -> loc.equals(place)).findFirst().get();
			}
			if (!validPlace.getEvents().contains(eventDesc)) {
				validPlace.getEvents().add(eventDesc);
			}
		});

		List<RegionEventDto> regionEvents = regionEventService.findDtoByTourId(tourId);
		regionEvents.forEach(event -> {
			RegionServiceDto service = regionServService.findDtoById(event.getService().getId());
			EventPlace place = new EventPlace(regionService.findDtoById(service.getRegionId()));
			EventDesc eventDesc = new EventDesc(service);
			eventDesc.setImageOneLink(convertImageIdToLink(service.getImageOne()));
			eventDesc.setImageTwoLink(convertImageIdToLink(service.getImageTwo()));
			EventPlace validPlace = new EventPlace();
			if (!presentation.getEventRegions().contains(place)) {
				presentation.getEventRegions().add(place);
				validPlace = place;
			} else {
				validPlace = presentation.getEventRegions().stream()
						.filter(loc -> loc.equals(place)).findFirst().get();
			}
			if (!validPlace.getEvents().contains(eventDesc)) {
				validPlace.getEvents().add(eventDesc);
			}
		});
		return presentation;
	}

	private Path convertImageIdToLink(String imageId) {
		if (!AppUtils.isBlank(imageId)) {
			return this.pathUtils.getImagesPath().resolve(imageId);
		}
		return null;
	}

}
