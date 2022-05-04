package ru.geekbrains.micecreator.controllers;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.micecreator.dto.basic.SearchParams;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.basic.list.SimpleTypes;
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
		return findServiceByType(type.toUpperCase()).findAllListDto();
	}

	@GetMapping("/by_name/{type}/{name}")
	public List<ListItemDto> findById(@PathVariable("type") String type, @NonNull @PathVariable("name") String name) {
		return findServiceByType(type.toUpperCase()).findListDtoByNamePart(name);
	}

	@GetMapping("/by_params/{type}")
	public List<ListItemDto> findById(@PathVariable("type") String type, @NonNull @RequestBody SearchParams params) {
		return findServiceByType(type.toUpperCase()).findBySearchParams(params);
	}

	@GetMapping("/by_id/{type}/{id}")
	public ListItemDto findById(@PathVariable("type") String type, @NonNull @PathVariable("id") Integer id) {
		return findServiceByType(type.toUpperCase()).findListDtoById(id);
	}

	@DeleteMapping("/{type}/{id}")
	public boolean deleteEntity(@PathVariable("type") String type, @NonNull @PathVariable("id") Integer id) {
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
			throw new RuntimeException("Неизвестный тип");
		}
	}

}
