package ru.geekbrains.micecreator.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
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
@Tag(name = "Базовые типы", description = "Контроллер для работы с компонентами туров в их базовом представлении. Включает типы размещения, авиакомпании, аэропорты, страны, локации, регионы, отели, номера, отельные и региональные услуги")
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
	@Operation(summary = "Запрос всех сущностей переданного типа")
	public List<ListItemDto> findAll(@PathVariable("type") String type) {
		logger.info(String.format("%s type requested", type));
		return findServiceByType(type.toUpperCase()).findAllListDto();
	}

	@GetMapping("/by_name/{type}/{name}")
	@Operation(summary = "Запрос всех сущностей переданного типа с именем, начинающимся на")
	public List<ListItemDto> findById(@PathVariable("type") String type, @NonNull @PathVariable("name") String name) {
		logger.info(String.format("%s type requested with name like %s", type, name));
		return findServiceByType(type.toUpperCase()).findListDtoByNamePart(name);
	}

	@GetMapping("/by_params/{type}")
	@Operation(summary = "Запрос всех сущностей переданного типа по параметрам", description = "Параметры передаются в зависимости от того, какая сущность ищется")
	public List<ListItemDto> findById(@PathVariable("type") String type,
	                                  @RequestParam(name = "name", required = false) @Parameter(description = "Имя") String namePart,
	                                  @RequestParam(name = "country", required = false) @Parameter(description = "Id страны") Integer countryId,
	                                  @RequestParam(name = "region", required = false) @Parameter(description = "Id региона") Integer regionId,
	                                  @RequestParam(name = "location", required = false) @Parameter(description = "Id локации") Integer locationId,
	                                  @RequestParam(name = "hotel", required = false) @Parameter(description = "Id отеля") Integer hotelId,
	                                  @RequestParam(name = "alter_name", required = false) @Parameter(description = "Используется ли альтернативное имя") boolean isUsingAlterNames) {
		SearchParams params = new SearchParams(namePart, countryId, regionId, locationId, hotelId, isUsingAlterNames);
		logger.info(String.format("%s type with params %s requested", type, params));
		return findServiceByType(type.toUpperCase()).findBySearchParams(params);
	}

	@GetMapping("/by_id/{type}/{id}")
	@Operation(summary = "Запрос сущности переданного типа с указанием id")
	public ListItemDto findById(@PathVariable("type") String type, @NonNull @PathVariable("id") Integer id) {
		logger.info(String.format("%s type requested with id %s", type, id));
		return findServiceByType(type.toUpperCase()).findListDtoById(id);
	}

	@GetMapping("/get_parent/{type}/{id}")
	@Operation(summary = "Запрос родительской сущности по ID текущей", description = "Работает для: аэропортов, отельных и региональных услуг, отелей, локаций, регионов, номеров")
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
	@Operation(summary = "Удаление сущности по типу с указанием ID")
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
