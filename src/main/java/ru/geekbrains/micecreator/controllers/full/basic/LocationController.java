package ru.geekbrains.micecreator.controllers.full.basic;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import ru.geekbrains.micecreator.dto.basic.full.LocationDto;
import ru.geekbrains.micecreator.service.LocationService;

@RestController
@RequestMapping("api/v1/location")
@AllArgsConstructor
@Tag(name = "Локации", description = "Контроллер для работы с локациями")
public class LocationController {

	private LocationService service;

	@GetMapping("/{id}")
	@Operation(summary = "Поиск локации по id")
	public LocationDto getById(@PathVariable("id") Integer id) {
		return service.findDtoById(id);
	}

	@PostMapping
	@Operation(summary = "Добавление новой локации")
	public LocationDto addNew(@NonNull @RequestBody LocationDto dto) {
		return service.createEntity(dto);
	}

	@PutMapping
	@Operation(summary = "Редактирование локации")
	public LocationDto edit(@NonNull @RequestBody LocationDto dto) {
		return service.editEntity(dto);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	@Operation(summary = "Удаление локации")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}
