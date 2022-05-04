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
import ru.geekbrains.micecreator.dto.basic.full.CountryDto;
import ru.geekbrains.micecreator.service.CountryService;

@RestController
@RequestMapping("api/v1/country")
@AllArgsConstructor
public class CountryController {

	private CountryService service;

	@GetMapping("/{id}")
	public CountryDto getById(@PathVariable("id") Integer id) {
		return service.findDtoById(id);
	}

	@PostMapping
	public CountryDto addNew(@NonNull @RequestBody CountryDto dto) {
		return service.createEntity(dto);
	}

	@PutMapping
	public CountryDto edit(@NonNull @RequestBody CountryDto dto) {
		return service.editEntity(dto);
	}

	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}
