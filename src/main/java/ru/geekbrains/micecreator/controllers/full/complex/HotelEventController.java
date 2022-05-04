package ru.geekbrains.micecreator.controllers.full.complex;

import lombok.AllArgsConstructor;
import lombok.NonNull;
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
import ru.geekbrains.micecreator.dto.complex.HotelEventDto;
import ru.geekbrains.micecreator.service.HotelEventService;

import java.util.List;

@RestController
@RequestMapping("api/v1/hotel_event")
@AllArgsConstructor
public class HotelEventController {

	private HotelEventService service;

	@GetMapping("/{id}")
	public HotelEventDto getById(@PathVariable("id") Integer id) {
		return service.findDtoById(id);
	}

	@GetMapping("/by_tour/{id}")
	public List<HotelEventDto> getByTour(@PathVariable("id") Integer id) {
		return service.findDtoByTourId(id);
	}

	@GetMapping("/by_params")
	public List<HotelEventDto> getByParams(@RequestParam(name = "hotel_service") Integer hotelServId,
	                                       @RequestParam(name = "first_date") String first,
	                                       @RequestParam(name = "second_date") String second) {
		ComplexParams params = new ComplexParams();
		params.setHotelServId(hotelServId);
		params.setFirstDate(first);
		params.setSecondDate(second);
		return service.findByParams(params);
	}

	@PostMapping
	public HotelEventDto addNew(@NonNull @RequestBody HotelEventDto dto) {
		return service.createEntity(dto);
	}

	@PutMapping
	public HotelEventDto edit(@NonNull @RequestBody HotelEventDto dto) {
		return service.editEntity(dto);
	}

	@DeleteMapping("/{id}")
	public boolean deleteById(@PathVariable("id") Integer id) {
		return service.deleteEntity(id);
	}

}
