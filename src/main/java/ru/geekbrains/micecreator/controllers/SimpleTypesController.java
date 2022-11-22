package ru.geekbrains.micecreator.controllers;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.micecreator.controllers.full.complex.TourController;
import ru.geekbrains.micecreator.dto.basic.SearchParams;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.basic.list.SimpleTypes;
import ru.geekbrains.micecreator.exceptions.BadInputException;
import ru.geekbrains.micecreator.service.AccommodationTypeService;
import ru.geekbrains.micecreator.service.AirlineService;
import ru.geekbrains.micecreator.service.AirportService;
import ru.geekbrains.micecreator.service.CountryService;
import ru.geekbrains.micecreator.service.HotelServService;
import ru.geekbrains.micecreator.service.HotelService;
import ru.geekbrains.micecreator.service.LocationService;
import ru.geekbrains.micecreator.service.RegionServService;
import ru.geekbrains.micecreator.service.RegionService;
import ru.geekbrains.micecreator.service.RoomService;
import ru.geekbrains.micecreator.service.prototypes.SimpleTypeService;

import java.util.List;

@RestController
@RequestMapping("api/v1/basic")
@AllArgsConstructor
public class SimpleTypesController {

	private static final Logger logger = LogManager.getLogger(SimpleTypesController.class);

	@Autowired
	private final AccommodationTypeService accommodationTypeService;
	@Autowired
	private final AirlineService airlineService;
	@Autowired
	private final AirportService airportService;
	@Autowired
	private final CountryService countryService;
	@Autowired
	private final HotelServService hotelServService;
	@Autowired
	private final HotelService hotelService;
	@Autowired
	private final LocationService locationService;
	@Autowired
	private final RegionService regionService;
	@Autowired
	private final RegionServService regionServService;
	@Autowired
	private final RoomService roomService;


	@GetMapping("/{type}")
	public List<ListItemDto> findAll(@PathVariable("type") String type) {
		logger.info(String.format("%s type requested", type));
		return findServiceByType(type.toUpperCase()).findAllListDto();
	}

	@GetMapping("/by_name/{type}/{name}")
	public List<ListItemDto> findById(@PathVariable("type") String type, @NonNull @PathVariable("name") String name) {
		logger.info(String.format("%s type requested with name like %s", type, name));
		return findServiceByType(type.toUpperCase()).findListDtoByNamePart(name);
	}

	@GetMapping("/by_params/{type}")
	public List<ListItemDto> findById(@PathVariable("type") String type,
	                                  @RequestParam(name = "name", required = false) String namePart,
	                                  @RequestParam(name = "country", required = false) Integer countryId,
	                                  @RequestParam(name = "region", required = false) Integer regionId,
	                                  @RequestParam(name = "location", required = false) Integer locationId,
	                                  @RequestParam(name = "hotel", required = false) Integer hotelId,
	                                  @RequestParam(name = "alter_name", required = false) boolean isUsingAlterNames) {
		SearchParams params = new SearchParams(namePart, countryId, regionId, locationId, hotelId, isUsingAlterNames);
		logger.info(String.format("%s type with params %s requested", type, params));
		return findServiceByType(type.toUpperCase()).findBySearchParams(params);
	}

	@GetMapping("/by_id/{type}/{id}")
	public ListItemDto findById(@PathVariable("type") String type, @NonNull @PathVariable("id") Integer id) {
		logger.info(String.format("%s type requested with id %s", type, id));
		return findServiceByType(type.toUpperCase()).findListDtoById(id);
	}

	@GetMapping("/get_parent/{type}/{id}")
	public ListItemDto findParentById(@PathVariable("type") String type, @NonNull @PathVariable("id") Integer id) {
		logger.info(String.format("Parent of %s type with id %s requested", type, id));
		try {
			switch (SimpleTypes.valueOf(type.toUpperCase())) {
				case AIRPORT:
					return regionService.findListDtoById(airportService.findParentId(id));
				case HOTEL_SERVICE:
					return hotelService.findListDtoById(hotelServService.findParentId(id));
				case HOTEL:
					return locationService.findListDtoById(hotelService.findParentId(id));
				case LOCATION:
					return regionService.findListDtoById(locationService.findParentId(id));
				case REGION:
					return countryService.findListDtoById(regionService.findParentId(id));
				case REGION_SERVICE:
					return regionService.findListDtoById(regionServService.findParentId(id));
				case ROOM:
					return hotelService.findListDtoById(roomService.findParentId(id));
				default:
					throw new IllegalStateException("Unexpected value: " + SimpleTypes.valueOf(type));
			}
		} catch (IllegalArgumentException e) {
			logger.error(String.format("Transmitted entity type %s is not supported.", type));
			throw new BadInputException(String.format("Transmitted entity type %s is not supported.", type));
		}
	}

	@DeleteMapping("/{type}/{id}")
	public boolean deleteEntity(@PathVariable("type") String type, @NonNull @PathVariable("id") Integer id) {
		logger.info(String.format("%s type with id %s deleted", type, id));
		return findServiceByType(type.toUpperCase()).deleteEntity(id);
	}

	private SimpleTypeService findServiceByType(String type) {
		try {
			switch (SimpleTypes.valueOf(type)) {
				case ACCOMM_TYPE:
					return accommodationTypeService;
				case AIRLINE:
					return airlineService;
				case AIRPORT:
					return airportService;
				case COUNTRY:
					return countryService;
				case HOTEL_SERVICE:
					return hotelServService;
				case HOTEL:
					return hotelService;
				case LOCATION:
					return locationService;
				case REGION:
					return regionService;
				case REGION_SERVICE:
					return regionServService;
				case ROOM:
					return roomService;
				default:
					throw new IllegalStateException("Unexpected value: " + SimpleTypes.valueOf(type));
			}
		} catch (IllegalArgumentException e) {
			logger.error(String.format("Transmitted entity type %s is not supported.", type));
			throw new BadInputException(String.format("Transmitted entity type %s is not supported.", type));
		}
	}

}
