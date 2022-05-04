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
import ru.geekbrains.micecreator.dto.basic.full.HotelServiceDto;
import ru.geekbrains.micecreator.service.HotelServService;

@RestController
@RequestMapping("api/v1/hotel_service")
@AllArgsConstructor
public class HotelServController {

	private HotelServService service;

	@GetMapping("/{id}")
	public HotelServiceDto getById(@PathVariable("id") Integer id) {
		return service.findDtoById(id);
	}

	@PostMapping
	public HotelServiceDto addNew(@NonNull @RequestBody HotelServiceDto dto) {
		return service.createEntity(dto);
	}

	@PutMapping
	public HotelServiceDto edit(@NonNull @RequestBody HotelServiceDto dto) {
		return service.editEntity(dto);
	}

	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}
