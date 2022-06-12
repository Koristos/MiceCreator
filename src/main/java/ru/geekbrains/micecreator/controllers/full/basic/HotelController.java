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
import ru.geekbrains.micecreator.dto.basic.full.HotelDto;
import ru.geekbrains.micecreator.service.HotelService;

@RestController
@RequestMapping("api/v1/hotel")
@AllArgsConstructor
public class HotelController {

	private HotelService service;

	@GetMapping("/{id}")
	public HotelDto getById(@PathVariable("id") Integer id) {
		return service.findDtoById(id);
	}

	@PostMapping
	public HotelDto addNew(@NonNull @RequestBody HotelDto dto) {
		return service.createEntity(dto);
	}

	@PutMapping
	public HotelDto edit(@NonNull @RequestBody HotelDto dto) {
		return service.editEntity(dto);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}
