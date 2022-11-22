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
import ru.geekbrains.micecreator.dto.complex.AccommodationDto;
import ru.geekbrains.micecreator.dto.complex.ComplexParams;
import ru.geekbrains.micecreator.service.AccommodationService;

import java.util.List;

@RestController
@RequestMapping("api/v1/accommodation")
@AllArgsConstructor
public class AccommodationController {

	private static final Logger logger = LogManager.getLogger(AccommodationController.class);

	@Autowired
	private AccommodationService service;

	@GetMapping("/{id}")
	public AccommodationDto getById(@PathVariable("id") Integer id) {
		logger.info(String.format("Accommodation Event by id %s requested", id));
		return service.findDtoById(id);
	}

	@GetMapping("/by_tour/{id}")
	public List<AccommodationDto> getByTour(@PathVariable("id") Integer id) {
		return service.findDtoByTourId(id);
	}

	@GetMapping("/by_params")
	public List<AccommodationDto> getByParams(@RequestParam(name = "room", required = false) Integer roomId,
	                                          @RequestParam(name = "hotel", required = false) Integer hotelId,
	                                          @RequestParam(name = "acc_type") Integer accTypeId,
	                                          @RequestParam(name = "first_date") String first,
	                                          @RequestParam(name = "second_date") String second,
	                                          @RequestParam(name = "first_date_creation") String firstCreation,
	                                          @RequestParam(name = "second_date_creation") String secondCreation) {
		logger.info(String.format("Accommodations with params room [ %s ] hotel [ %s ] acc_type [ %s ] first_date [ %s ] second_date [ %s ] first_date_creation [ %s ] second_date_creation [ %s ] requested",
				roomId, hotelId, accTypeId, first, second, firstCreation, secondCreation));
		ComplexParams params = new ComplexParams();
		params.setRoomId(roomId);
		params.setHotelId(hotelId);
		params.setAccTypeId(accTypeId);
		params.setFirstDateFromString(first);
		params.setSecondDateFromString(second);
		params.setFirstDateOfCreationFromString(firstCreation);
		params.setSecondDateOfCreationFromString(secondCreation);
		return service.findByParams(params);
	}

	@PostMapping
	public AccommodationDto addNew(@NonNull @RequestBody AccommodationDto dto) {
		logger.info(String.format("New Accommodation added %s", dto));
		return service.createEntity(dto);
	}

	@PutMapping
	public AccommodationDto edit(@NonNull @RequestBody AccommodationDto dto) {
		logger.info(String.format("Accommodation edit %s", dto));
		return service.editEntity(dto);
	}

	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable("id") Integer id) {
		logger.info(String.format("Accommodation with id %s deleted", id));
		return service.deleteEntity(id);
	}

}
