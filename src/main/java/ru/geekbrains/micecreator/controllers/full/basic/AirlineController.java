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
import ru.geekbrains.micecreator.dto.basic.full.AirlineDto;
import ru.geekbrains.micecreator.service.AirlineService;

@RestController
@RequestMapping("api/v1/airline")
@AllArgsConstructor
@Tag(name = "Авиакомпании", description = "Контроллер для работы с авиакомпаниями")
public class AirlineController {

	private AirlineService service;

	@GetMapping("/{id}")
	@Operation(summary = "Поиск авиакомпании по id")
	public AirlineDto getById(@PathVariable("id") Integer id) {
		return service.findDtoById(id);
	}

	@PostMapping
	@Operation(summary = "Добавление новой авиакомпании")
	public AirlineDto addNew(@NonNull @RequestBody AirlineDto dto) {
		return service.createEntity(dto);
	}

	@PutMapping
	@Operation(summary = "Редактирование авиакомпании")
	public AirlineDto edit(@NonNull @RequestBody AirlineDto dto) {
		return service.editEntity(dto);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	@Operation(summary = "Удаление авиакомпании")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}
