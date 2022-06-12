package ru.geekbrains.micecreator.controllers.full.basic;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.micecreator.dto.basic.full.AirportDto;
import ru.geekbrains.micecreator.service.AirportService;

@RestController
@RequestMapping("api/v1/airport")
@AllArgsConstructor
public class AirportController {

	private AirportService service;

	@GetMapping("/{id}")
	public AirportDto getById(@PathVariable("id") Integer id) {
		return service.findDtoById(id);
	}

	@PostMapping
	public AirportDto addNew(@NonNull @RequestBody AirportDto dto) {
		return service.createEntity(dto);
	}

	@PutMapping
	public AirportDto edit(@NonNull @RequestBody AirportDto dto) {
		return service.editEntity(dto);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}
