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
import ru.geekbrains.micecreator.dto.basic.full.CountryDto;
import ru.geekbrains.micecreator.service.CountryService;

@RestController
@RequestMapping("api/v1/country")
@AllArgsConstructor
@Tag(name = "Страны", description = "Контроллер для работы со странами")
public class CountryController {

	private CountryService service;

	@GetMapping("/{id}")
	@Operation(summary = "Поиск страны по id")
	public CountryDto getById(@PathVariable("id") Integer id) {
		return service.findDtoById(id);
	}

	@PostMapping
	@Operation(summary = "Добавление новой страны")
	public CountryDto addNew(@NonNull @RequestBody CountryDto dto) {
		return service.createEntity(dto);
	}

	@PutMapping
	@Operation(summary = "Редактирование страны")
	public CountryDto edit(@NonNull @RequestBody CountryDto dto) {
		return service.editEntity(dto);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	@Operation(summary = "Удаление страны")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}
