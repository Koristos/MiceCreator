package ru.geekbrains.micecreator.controllers.full.complex;

import lombok.AllArgsConstructor;
import lombok.NonNull;
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

	private AccommodationService service;

	@GetMapping("/{id}")
	public AccommodationDto getById(@PathVariable("id") Integer id) {
		return service.findDtoById(id);
	}

	@GetMapping("/by_tour/{id}")
	public List<AccommodationDto> getByTour(@PathVariable("id") Integer id) {
		return service.findDtoByTourId(id);
	}

	@GetMapping("/by_params")
	public List<AccommodationDto> getByParams(@RequestParam(name = "room") Integer roomId,
	                                          @RequestParam(name = "acc_type") Integer accTypeId,
	                                          @RequestParam(name = "first_date") String first,
	                                          @RequestParam(name = "second_date") String second) {
		ComplexParams params = new ComplexParams();
		params.setRoomId(roomId);
		params.setAccTypeId(accTypeId);
		params.setFirstDate(first);
		params.setSecondDate(second);
		return service.findByParams(params);
	}

	@PostMapping
	public AccommodationDto addNew(@NonNull @RequestBody AccommodationDto dto) {
		return service.createEntity(dto);
	}

	@PutMapping
	public AccommodationDto edit(@NonNull @RequestBody AccommodationDto dto) {
		return service.editEntity(dto);
	}

	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}
