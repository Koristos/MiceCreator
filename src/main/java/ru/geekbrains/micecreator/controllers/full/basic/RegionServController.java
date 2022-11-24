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
import ru.geekbrains.micecreator.dto.basic.full.RegionServiceDto;
import ru.geekbrains.micecreator.service.RegionServService;

@RestController
@RequestMapping("api/v1/region_service")
@AllArgsConstructor
@Tag(name = "Региональные услуги", description = "Контроллер для работы с региональными услугами")
public class RegionServController {

	private RegionServService service;

	@GetMapping("/{id}")
	@Operation(summary = "Поиск региональной услуги по id")
	public RegionServiceDto getById(@PathVariable("id") Integer id) {
		return service.findDtoById(id);
	}

	@PostMapping
	@Operation(summary = "Добавление новой региональный услуги")
	public RegionServiceDto addNew(@NonNull @RequestBody RegionServiceDto dto) {
		return service.createEntity(dto);
	}

	@PutMapping
	@Operation(summary = "Редактирование региональный услуги")
	public RegionServiceDto edit(@NonNull @RequestBody RegionServiceDto dto) {
		return service.editEntity(dto);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	@Operation(summary = "Удаление региональной услуги")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}
