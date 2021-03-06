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
import ru.geekbrains.micecreator.dto.basic.full.AccommTypeDto;
import ru.geekbrains.micecreator.service.AccommodationTypeService;

@RestController
@RequestMapping("api/v1/accomm_type")
@AllArgsConstructor
public class AccommodationTypeController {

	private AccommodationTypeService service;

	@GetMapping("/{id}")
	public AccommTypeDto getById(@PathVariable("id") Integer id) {
		return service.findDtoById(id);
	}

	@PostMapping
	public AccommTypeDto addNew(@NonNull @RequestBody AccommTypeDto dto) {
		return service.createEntity(dto);
	}

	@PutMapping
	public AccommTypeDto edit(@NonNull @RequestBody AccommTypeDto dto) {
		return service.editEntity(dto);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}
