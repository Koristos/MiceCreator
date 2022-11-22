package ru.geekbrains.micecreator.controllers.full.complex;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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

	private static final Logger logger = LogManager.getLogger(FlightController.class);

	@Autowired
	private FlightService service;

	@GetMapping("/{id}")
	public FlightDto getById(@PathVariable("id") Integer id) {
		logger.info(String.format("Flight Event by id %s requested", id));
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
		logger.info(String.format("Flights with params airline [ %s ] dep_port [ %s ] arr_port [ %s ] first_date [ %s ] second_date [ %s ] first_date_creation [ %s ] second_date_creation [ %s ] requested",
				airlineId, departureAirportId, arrivalAirportId, first, second, firstCreation, secondCreation));
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
		logger.info(String.format("New Flight added %s", dto));
		return service.createEntity(dto);
	}

	@PutMapping
	public FlightDto edit(@NonNull @RequestBody FlightDto dto) {
		logger.info(String.format("Flight edit %s", dto));
		return service.editEntity(dto);
	}

	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable("id") Integer id) {
		logger.info(String.format("Flight with id %s deleted", id));
		return service.deleteEntity(id);
	}

}
