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
import ru.geekbrains.micecreator.dto.basic.full.LocationDto;
import ru.geekbrains.micecreator.service.LocationService;

@RestController
@RequestMapping("api/v1/location")
@AllArgsConstructor
public class LocationController {

	private LocationService service;

	@GetMapping("/{id}")
	public LocationDto getById(@PathVariable("id") Integer id) {
		return service.findDtoById(id);
	}

	@PostMapping
	public LocationDto addNew(@NonNull @RequestBody LocationDto dto) {
		return service.createEntity(dto);
	}

	@PutMapping
	public LocationDto edit(@NonNull @RequestBody LocationDto dto) {
		return service.editEntity(dto);
	}

	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}