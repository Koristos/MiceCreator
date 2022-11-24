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
import ru.geekbrains.micecreator.dto.basic.full.HotelServiceDto;
import ru.geekbrains.micecreator.service.HotelServService;

@RestController
@RequestMapping("api/v1/hotel_service")
@AllArgsConstructor
@Tag(name = "Отельные услуги", description = "Контроллер для работы с отельными услугами")
public class HotelServController {

	private HotelServService service;

	@GetMapping("/{id}")
	@Operation(summary = "Поиск отельной услуги по id")
	public HotelServiceDto getById(@PathVariable("id") Integer id) {
		return service.findDtoById(id);
	}

	@PostMapping
	@Operation(summary = "Добавление новой отельной услуги")
	public HotelServiceDto addNew(@NonNull @RequestBody HotelServiceDto dto) {
		return service.createEntity(dto);
	}

	@PutMapping
	@Operation(summary = "Редактирование отельной услуги")
	public HotelServiceDto edit(@NonNull @RequestBody HotelServiceDto dto) {
		return service.editEntity(dto);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	@Operation(summary = "Удаление отельной услуги")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}
