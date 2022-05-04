package ru.geekbrains.micecreator.controllers.full.basic;

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
import ru.geekbrains.micecreator.dto.basic.full.AirlineDto;
import ru.geekbrains.micecreator.service.AirlineService;

@RestController
@RequestMapping("api/v1/airline")
@AllArgsConstructor
public class AirlineController {

	private AirlineService service;

	@GetMapping("/{id}")
	public AirlineDto getById(@PathVariable("id") Integer id) {
		return service.findDtoById(id);
	}

	@PostMapping
	public AirlineDto addNew(@NonNull @RequestBody AirlineDto dto) {
		return service.createEntity(dto);
	}

	@PutMapping
	public AirlineDto edit(@NonNull @RequestBody AirlineDto dto) {
		return service.editEntity(dto);
	}

	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}
