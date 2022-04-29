package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.complex.RegionEventDto;
import ru.geekbrains.micecreator.models.complex.RegionEvent;
import ru.geekbrains.micecreator.repository.RegionEventRepo;
import ru.geekbrains.micecreator.service.prototypes.ComplexTypeService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RegionEventService extends ComplexTypeService<RegionEventDto, RegionEvent> {

	@Autowired
	private RegionEventRepo regionEventRepo;
	@Autowired
	private final RegionServService regionServService;
	@Autowired
	private final TourService tourService;


	public List<RegionEventDto> findDtoByServiceIdInDates(Integer regionServId, Date firstDate, Date secondDate) {
		checkInput(regionServId, firstDate, secondDate);
		checkDates(firstDate, secondDate);
		return findByServiceIdInDates(regionServId, firstDate, secondDate).stream()
				.map(this::mapToDto).collect(Collectors.toList());
	}


	public List<RegionEvent> findByServiceIdInDates(Integer regionServId, Date firstDate, Date secondDate) {
		return regionEventRepo.findByServiceIdAndDateBetween(regionServId, firstDate, secondDate);
	}

	@Override
	public List<RegionEvent> findAll() {
		return regionEventRepo.findAll();
	}

	@Override
	public List<RegionEvent> findByTour(Integer tourId) {
		return regionEventRepo.findByTourId(tourId);
	}

	@Override
	public RegionEvent findById(Integer id) {
		return regionEventRepo.findById(id).orElse(null);
	}

	@Override
	public RegionEvent save(RegionEvent regionEvent) {
		RegionEvent result = regionEventRepo.save(regionEvent);
		tourService.calculate(result.getTour().getId());
		return result;
	}

	@Override
	public boolean deleteById(Integer id) {
		RegionEvent result = findById(id);
		if (result == null){
			return false;
		}
		tourService.calculate(result.getTour().getId());
		regionEventRepo.deleteById(id);
		return !regionEventRepo.existsById(id);
	}

	@Override
	protected RegionEventDto mapToDto(RegionEvent entity) {
		RegionEventDto dto = new RegionEventDto();
		dto.setId(entity.getId());
		dto.setDate(entity.getDate());
		dto.setPax(entity.getPax());
		dto.setPrice(entity.getPrice());
		dto.setTourId(entity.getTour().getId());
		dto.setService(regionServService.findListDtoById(entity.getService().getId()));
		return dto;
	}

	@Override
	protected RegionEvent mapToEntity(RegionEventDto dto) {
		RegionEvent event = new RegionEvent();
		event.setId(dto.getId());
		event.setDate(dto.getDate());
		event.setPax(dto.getPax());
		event.setPrice(dto.getPrice());
		event.setTour(tourService.findById(dto.getTourId()));
		event.setService(regionServService.findById(dto.getService().getId()));
		return event;
	}

}
