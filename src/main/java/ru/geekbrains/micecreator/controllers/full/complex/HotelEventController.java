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
import ru.geekbrains.micecreator.dto.complex.HotelEventDto;
import ru.geekbrains.micecreator.service.HotelEventService;

import java.util.List;

@RestController
@RequestMapping("api/v1/hotel_event")
@AllArgsConstructor
@Tag(name = "Отельные события", description = "Контроллер для работы с отельными событиями")
public class HotelEventController {

	private static final Logger logger = LogManager.getLogger(HotelEventController.class);

	@Autowired
	private HotelEventService service;

	@GetMapping("/{id}")
	@Operation(summary = "Поиск Отельного события по id")
	public HotelEventDto getById(@PathVariable("id") Integer id) {
		logger.info(String.format("Hotel Event by id %s requested", id));
		return service.findDtoById(id);
	}

	@GetMapping("/by_tour/{id}")
	@Operation(summary = "Поиск Отельного события по id тура")
	public List<HotelEventDto> getByTour(@PathVariable("id") Integer id) {
		return service.findDtoByTourId(id);
	}

	@GetMapping("/by_params")
	@Operation(summary = "Поиск Отельного события по параметрам")
	public List<HotelEventDto> getByParams(@RequestParam(name = "hotel_service") @Parameter(description = "Id отельной услуги") Integer hotelServId,
	                                       @RequestParam(name = "first_date") @Parameter(description = "Первая дата диапазона поиска dd.MM.yyyy") String first,
	                                       @RequestParam(name = "second_date") @Parameter(description = "Вторая дата диапазона поиска dd.MM.yyyy") String second,
	                                       @RequestParam(name = "first_date_creation") @Parameter(description = "Первая дата диапазона поиска (дата создания) dd.MM.yyyy") String firstCreation,
	                                       @RequestParam(name = "second_date_creation") @Parameter(description = "Вторая дата диапазона поиска (дата создания) dd.MM.yyyy") String secondCreation) {
		logger.info(String.format("Hotel Events with params hotel_service [ %s ] first_date [ %s ] second_date [ %s ] first_date_creation [ %s ] second_date_creation [ %s ] requested",
				hotelServId, first, second, firstCreation, secondCreation));
		ComplexParams params = new ComplexParams();
		params.setHotelServId(hotelServId);
		params.setFirstDateFromString(first);
		params.setSecondDateFromString(second);
		params.setFirstDateOfCreationFromString(firstCreation);
		params.setSecondDateOfCreationFromString(secondCreation);
		return service.findByParams(params);
	}

	@PostMapping
	@Operation(summary = "Добавление нового Отельного события")
	public HotelEventDto addNew(@NonNull @RequestBody HotelEventDto dto) {
		logger.info(String.format("New Hotel Event added %s", dto));
		return service.createEntity(dto);
	}

	@PutMapping
	@Operation(summary = "Редактирование Отельного события")
	public HotelEventDto edit(@NonNull @RequestBody HotelEventDto dto) {
		logger.info(String.format("Hotel Event edit %s", dto));
		return service.editEntity(dto);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Удаление Отельного события")
	public boolean deleteById(@PathVariable("id") Integer id) {
		logger.info(String.format("Hotel Event with id %s deleted", id));
		return service.deleteEntity(id);
	}

}
