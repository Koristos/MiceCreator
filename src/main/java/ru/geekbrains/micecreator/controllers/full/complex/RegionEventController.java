package ru.geekbrains.micecreator.controllers.full.complex;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.micecreator.dto.complex.ComplexParams;
import ru.geekbrains.micecreator.dto.complex.RegionEventDto;
import ru.geekbrains.micecreator.service.RegionEventService;

import java.util.List;

@RestController
@RequestMapping("api/v1/region_event")
@AllArgsConstructor
public class RegionEventController {

	@Autowired
	private RegionEventService service;

	@GetMapping("/{id}")
	public RegionEventDto getById(@PathVariable("id") Integer id) {
		return service.findDtoById(id);
	}

	@GetMapping("/by_tour/{id}")
	public List<RegionEventDto> getByTour(@PathVariable("id") Integer id) {
		return service.findDtoByTourId(id);
	}

	@GetMapping("/by_params")
	public List<RegionEventDto> getByParams(@RequestParam(name = "region_service") Integer regionServId,
	                                        @RequestParam(name = "first_date") String first,
	                                        @RequestParam(name = "second_date") String second) {
		ComplexParams params = new ComplexParams();
		params.setRegionServId(regionServId);
		params.setFirstDateFromString(first);
		params.setSecondDateFromString(second);
		return service.findByParams(params);
	}

	@PostMapping
	public RegionEventDto addNew(@NonNull @RequestBody RegionEventDto dto) {
		return service.createEntity(dto);
	}

	@PutMapping
	public RegionEventDto edit(@NonNull @RequestBody RegionEventDto dto) {
		return service.editEntity(dto);
	}

	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}
