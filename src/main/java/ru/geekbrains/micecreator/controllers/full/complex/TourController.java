package ru.geekbrains.micecreator.controllers.full.complex;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.micecreator.dto.complex.ComplexParams;
import ru.geekbrains.micecreator.dto.complex.TourDto;
import ru.geekbrains.micecreator.dto.complex.TourDtoFull;
import ru.geekbrains.micecreator.service.AccommodationService;
import ru.geekbrains.micecreator.service.FlightService;
import ru.geekbrains.micecreator.service.HotelEventService;
import ru.geekbrains.micecreator.service.RegionEventService;
import ru.geekbrains.micecreator.service.TourService;

import java.util.List;

@RestController
@RequestMapping("api/v1/tour")
@AllArgsConstructor
public class TourController {

	@Autowired
	private TourService service;
	@Autowired
	private FlightService flightService;
	@Autowired
	private AccommodationService accommodationService;
	@Autowired
	private RegionEventService regionEventService;
	@Autowired
	private HotelEventService hotelEventService;

	@GetMapping("/{id}")
	public TourDtoFull getById(@PathVariable("id") Integer id) {
		TourDtoFull fullDto = new TourDtoFull();
		fullDto.setTour(service.findDtoById(id));
		fullDto.setFlights(flightService.findDtoByTourId(id));
		fullDto.setAccommodations(accommodationService.findDtoByTourId(id));
		fullDto.setRegionEvents(regionEventService.findDtoByTourId(id));
		fullDto.setHotelEvents(hotelEventService.findDtoByTourId(id));
		return fullDto;
	}

	@GetMapping("/by_params")
	public List<TourDto> getByParams(@RequestParam(name = "country", required = false) Integer countryId,
	                                 @RequestParam(name = "first_date") String first,
	                                 @RequestParam(name = "second_date") String second) {
		ComplexParams params = new ComplexParams();
		params.setCountryId(countryId);
		params.setFirstDateFromString(first);
		params.setSecondDateFromString(second);
		return service.findByParams(params);
	}

	@PostMapping
	public TourDto addNew(@NonNull @RequestBody TourDto dto) {
		return service.createEntity(dto);
	}

	@PutMapping
	public TourDto edit(@NonNull @RequestBody TourDto dto) {
		return service.editEntity(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LEADER')")
	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}
