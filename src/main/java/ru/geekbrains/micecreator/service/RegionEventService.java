package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.complex.ComplexParams;
import ru.geekbrains.micecreator.dto.complex.RegionEventDto;
import ru.geekbrains.micecreator.dto.complex.estimate.RegionEventEstimate;
import ru.geekbrains.micecreator.models.complex.RegionEvent;
import ru.geekbrains.micecreator.repository.RegionEventRepo;
import ru.geekbrains.micecreator.service.prototypes.ComplexTypeService;

import java.math.BigDecimal;
import java.time.LocalDate;
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
	@Autowired
	private final RegionService regionService;

	/**
	 * Метод ищет Региональные события по параметрам:
	 * - по ID Региональной услуги
	 * - в диапазоне между датами по дате начала события
	 * - в диапазоне между датами создания по дате создания
	 * @param params параметры для поиска
	 * @return список региональных событий в виде DTO
	 */
	public List<RegionEventDto> findByParams(ComplexParams params) {
		checkInput(params.getRegionServId(), params.getFirstDate(), params.getSecondDate());
		checkDates(params.getFirstDate(), params.getSecondDate());
		checkDates(params.getFirstDateOfCreation(), params.getSecondDateOfCreation());
		return findByServiceIdInDates(params.getRegionServId(), params.getFirstDate(), params.getSecondDate(), params.getFirstDateOfCreation(),
				params.getSecondDateOfCreation()).stream()
				.map(this::mapToDto).collect(Collectors.toList());
	}


	public List<RegionEvent> findByServiceIdInDates(Integer regionServId, LocalDate firstDate, LocalDate secondDate,
	                                                LocalDate firstCreationDate, LocalDate secondCreationDate) {
		return regionEventRepo.findByServiceIdAndDateBetweenAndCreationDateBetween(regionServId, firstDate, secondDate, firstCreationDate, secondCreationDate);
	}

	public List<RegionEventEstimate> makeEstimate(Integer tourId) {
		return findByTour (tourId).stream().map(this::mapToEstimate).collect(Collectors.toList());

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
		regionEventRepo.deleteById(id);
		tourService.calculate(result.getTour().getId());
		return !regionEventRepo.existsById(id);
	}

	@Override
	protected RegionEventDto mapToDto(RegionEvent entity) {
		RegionEventDto dto = new RegionEventDto();
		dto.setId(entity.getId());
		dto.setEventDate(entity.getDate());
		dto.setPax(entity.getPax());
		dto.setPrice(entity.getPrice());
		dto.setTourId(entity.getTour().getId());
		dto.setService(regionServService.findListDtoById(entity.getService().getId()));
		dto.setTotal(entity.getPrice().multiply(BigDecimal.valueOf(entity.getPax())));
		dto.setNettoPrice(entity.getNettoPrice());
		dto.setCreationDate(entity.getCreationDate());
		dto.setNettoTotal(entity.getNettoPrice().multiply(BigDecimal.valueOf(entity.getPax())));
		return dto;
	}

	@Override
	protected RegionEvent mapToEntity(RegionEventDto dto) {
		RegionEvent event = new RegionEvent();
		event.setId(dto.getId());
		event.setDate(dto.getEventDate());
		event.setPax(dto.getPax());
		event.setPrice(dto.getPrice());
		event.setTour(tourService.findById(dto.getTourId()));
		event.setService(regionServService.findById(dto.getService().getId()));
		event.setNettoPrice(dto.getNettoPrice());
		event.setCreationDate(dto.getCreationDate());
		return event;
	}

	private RegionEventEstimate mapToEstimate (RegionEvent regionEvent) {
		RegionEventEstimate estimate = new RegionEventEstimate();
		estimate.setEventDate(regionEvent.getDate());
		estimate.setPax(regionEvent.getPax());
		estimate.setPrice(regionEvent.getPrice());
		estimate.setService(regionServService.findListDtoById(regionEvent.getService().getId()));
		estimate.setRegion(regionService.findListDtoById(regionServService.findParentId(regionEvent.getService().getId())));
		return estimate;
	}

}
