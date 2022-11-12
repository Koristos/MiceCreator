package ru.geekbrains.micecreator.controllers.full.complex;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
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
import ru.geekbrains.micecreator.dto.complex.FlightDto;
import ru.geekbrains.micecreator.service.FlightService;

import java.util.List;

@RestController
@RequestMapping("api/v1/flight")
@AllArgsConstructor
public class FlightController {

	@Autowired
	private FlightService service;

	@GetMapping("/{id}")
	public FlightDto getById(@PathVariable("id") Integer id) {
		return service.findDtoById(id);
	}

	@GetMapping("/by_tour/{id}")
	public List<FlightDto> getByTour(@PathVariable("id") Integer id) {
		return service.findDtoByTourId(id);
	}

	@GetMapping("/by_params")
	public List<FlightDto> getByParams(@RequestParam(name = "airline", required = false) Integer airlineId,
	                                   @RequestParam(name = "dep_port") Integer departureAirportId,
	                                   @RequestParam(name = "arr_port") Integer arrivalAirportId,
	                                   @RequestParam(name = "first_date") String first,
	                                   @RequestParam(name = "second_date") String second,
	                                   @RequestParam(name = "first_date_creation") String firstCreation,
	                                   @RequestParam(name = "second_date_creation") String secondCreation) {
		ComplexParams params = new ComplexParams();
		params.setAirlineId(airlineId);
		params.setDepartureAirportId(departureAirportId);
		params.setArrivalAirportId(arrivalAirportId);
		params.setFirstDateFromString(first);
		params.setSecondDateFromString(second);
		params.setFirstDateOfCreationFromString(firstCreation);
		params.setSecondDateOfCreationFromString(secondCreation);
		return service.findByParams(params);
	}

	@PostMapping
	public FlightDto addNew(@NonNull @RequestBody FlightDto dto) {
		return service.createEntity(dto);
	}

	@PutMapping
	public FlightDto edit(@NonNull @RequestBody FlightDto dto) {
		return service.editEntity(dto);
	}

	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}
