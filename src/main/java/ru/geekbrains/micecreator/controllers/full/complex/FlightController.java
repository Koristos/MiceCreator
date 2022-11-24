package ru.geekbrains.micecreator.controllers.full.complex;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Перелеты", description = "Контроллер для работы с перелетами")
public class FlightController {

	private static final Logger logger = LogManager.getLogger(FlightController.class);

	@Autowired
	private FlightService service;

	@GetMapping("/{id}")
	@Operation(summary = "Поиск перелета по id")
	public FlightDto getById(@PathVariable("id") Integer id) {
		logger.info(String.format("Flight Event by id %s requested", id));
		return service.findDtoById(id);
	}

	@GetMapping("/by_tour/{id}")
	@Operation(summary = "Поиск перелета по id тура")
	public List<FlightDto> getByTour(@PathVariable("id") Integer id) {
		return service.findDtoByTourId(id);
	}

	@GetMapping("/by_params")
	@Operation(summary = "Поиск перелета по параметрам")
	public List<FlightDto> getByParams(@RequestParam(name = "airline", required = false) @Parameter(description = "Id авиакомпании") Integer airlineId,
	                                   @RequestParam(name = "dep_port") @Parameter(description = "Id аэропорта вылета") Integer departureAirportId,
	                                   @RequestParam(name = "arr_port") @Parameter(description = "Id аэропорта прилета") Integer arrivalAirportId,
	                                   @RequestParam(name = "first_date") @Parameter(description = "Первая дата диапазона поиска dd.MM.yyyy") String first,
	                                   @RequestParam(name = "second_date") @Parameter(description = "Вторая дата диапазона поиска dd.MM.yyyy") String second,
	                                   @RequestParam(name = "first_date_creation") @Parameter(description = "Первая дата диапазона поиска (дата создания) dd.MM.yyyy") String firstCreation,
	                                   @RequestParam(name = "second_date_creation") @Parameter(description = "Вторая дата диапазона поиска (дата создания) dd.MM.yyyy") String secondCreation) {
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
	@Operation(summary = "Добавление нового перелета")
	public FlightDto addNew(@NonNull @RequestBody FlightDto dto) {
		logger.info(String.format("New Flight added %s", dto));
		return service.createEntity(dto);
	}

	@PutMapping
	@Operation(summary = "Редактирование перелета")
	public FlightDto edit(@NonNull @RequestBody FlightDto dto) {
		logger.info(String.format("Flight edit %s", dto));
		return service.editEntity(dto);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Удаление перелета")
	public boolean deleteById(@PathVariable("id") Integer id) {
		logger.info(String.format("Flight with id %s deleted", id));
		return service.deleteEntity(id);
	}

}
