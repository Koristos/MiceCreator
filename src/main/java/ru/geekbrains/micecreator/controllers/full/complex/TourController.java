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
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.micecreator.dto.complex.ComplexParams;
import ru.geekbrains.micecreator.dto.complex.TourDto;
import ru.geekbrains.micecreator.service.TourService;

import java.util.List;

@RestController
@RequestMapping("api/v1/tour")
@AllArgsConstructor
public class TourController {

	private TourService service;

	@GetMapping("/{id}")
	public TourDto getById(@PathVariable("id") Integer id) {
		return service.findDtoById(id);
	}

	@GetMapping("/by_params")
	public List<TourDto> getByParams(@NonNull @RequestBody ComplexParams params) {
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

	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}
