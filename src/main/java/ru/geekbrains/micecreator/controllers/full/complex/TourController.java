package ru.geekbrains.micecreator.controllers.full.complex;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
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
@Tag(name = "Туры", description = "Контроллер для работы с турами")
public class TourController {

	private static final Logger logger = LogManager.getLogger(TourController.class);

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
	@Operation (summary = "Запрос тура по id")
	public TourDtoFull getById(@PathVariable("id") Integer id) {
		logger.info(String.format("Tour by id %s requested", id));
		TourDtoFull fullDto = new TourDtoFull();
		fullDto.setTour(service.findDtoById(id));
		fullDto.setFlights(flightService.findDtoByTourId(id));
		fullDto.setAccommodations(accommodationService.findDtoByTourId(id));
		fullDto.setRegionEvents(regionEventService.findDtoByTourId(id));
		fullDto.setHotelEvents(hotelEventService.findDtoByTourId(id));
		return fullDto;
	}

	@GetMapping("/by_params")
	@Operation (summary = "Поиск туров, подходящих под параметры")
	public List<TourDto> getByParams(@RequestParam(name = "country", required = false) @Parameter(description = "ID страны тура") Integer countryId,
	                                 @RequestParam(name = "first_date") @Parameter(description = "Первая дата диапазона поиска dd.MM.yyyy") String first,
	                                 @RequestParam(name = "second_date") @Parameter(description = "Вторая дата диапазона поиска dd.MM.yyyy") String second,
	                                 @RequestParam(name = "user_name") @Parameter(description = "Имя пользователя-создателя") String name) {
		logger.info(String.format("Tours with params country [ %s ] first_date [ %s ] second_date [ %s ] user_name [ %s ] requested",
				countryId, first, second, name));
		ComplexParams params = new ComplexParams();
		params.setCountryId(countryId);
		params.setFirstDateFromString(first);
		params.setSecondDateFromString(second);
		params.setUserName(name);
		return service.findByParams(params);
	}

	@PostMapping
	@Operation (summary = "Добавление нового тура")
	public TourDto addNew(@NonNull @RequestBody TourDto dto) {
		logger.info(String.format("New tour added %s", dto));
		return service.createEntity(dto);
	}

	@PutMapping
	@Operation (summary = "Изменение данных существующего тура")
	public TourDto edit(@NonNull @RequestBody TourDto dto) {
		logger.info(String.format("Tour edit %s", dto));
		return service.editEntity(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_LEADER')")
	@DeleteMapping("/{id}")
	@Operation (summary = "Удаление тура, доступно не всем ролям")
	public boolean deleteById(@PathVariable("id") Integer id) {
		logger.info(String.format("Tour with id %s deleted", id));
		return service.deleteEntity(id);
	}

}
