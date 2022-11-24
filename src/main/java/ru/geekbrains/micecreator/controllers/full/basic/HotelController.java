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
import ru.geekbrains.micecreator.dto.basic.full.HotelDto;
import ru.geekbrains.micecreator.service.HotelService;

@RestController
@RequestMapping("api/v1/hotel")
@AllArgsConstructor
@Tag(name = "Отели", description = "Контроллер для работы с отелями")
public class HotelController {

	private HotelService service;

	@GetMapping("/{id}")
	@Operation(summary = "Поиск отеля по id")
	public HotelDto getById(@PathVariable("id") Integer id) {
		return service.findDtoById(id);
	}

	@PostMapping
	@Operation(summary = "Добавление нового отеля")
	public HotelDto addNew(@NonNull @RequestBody HotelDto dto) {
		return service.createEntity(dto);
	}

	@PutMapping
	@Operation(summary = "Редактирование отеля")
	public HotelDto edit(@NonNull @RequestBody HotelDto dto) {
		return service.editEntity(dto);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	@Operation(summary = "Удаление отеля")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}
