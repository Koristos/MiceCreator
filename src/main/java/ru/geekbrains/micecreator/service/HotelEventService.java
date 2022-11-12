package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.complex.ComplexParams;
import ru.geekbrains.micecreator.dto.complex.HotelEventDto;
import ru.geekbrains.micecreator.dto.complex.estimate.HotelEventEstimate;
import ru.geekbrains.micecreator.models.complex.HotelEvent;
import ru.geekbrains.micecreator.repository.HotelEventRepo;
import ru.geekbrains.micecreator.service.prototypes.ComplexTypeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HotelEventService extends ComplexTypeService<HotelEventDto, HotelEvent> {

	@Autowired
	private final HotelEventRepo hotelEventRepo;
	@Autowired
	private final HotelServService hotelServService;
	@Autowired
	private final TourService tourService;
	@Autowired
	private final HotelService hotelService;


	public List<HotelEventDto> findByParams(ComplexParams params) {
		checkInput(params.getHotelServId(), params.getFirstDate(), params.getSecondDate());
		checkDates(params.getFirstDate(), params.getSecondDate());
		checkDates(params.getFirstDateOfCreation(), params.getSecondDateOfCreation());
		return findByServiceIdInDates(params.getHotelServId(), params.getFirstDate(), params.getSecondDate(), params.getFirstDateOfCreation(),
				params.getSecondDateOfCreation()).stream()
				.map(this::mapToDto).collect(Collectors.toList());
	}

	protected List<HotelEvent> findByServiceIdInDates(Integer hotelServId, LocalDate firstDate, LocalDate secondDate,
	                                                  LocalDate firstCreationDate, LocalDate secondCreationDate) {
		return hotelEventRepo.findByServiceIdAndDateBetweenAndCreationDateBetween(hotelServId, firstDate, secondDate, firstCreationDate, secondCreationDate);
	}

	public List<HotelEventEstimate> makeEstimate(Integer tourId) {
		return findByTour (tourId).stream().map(this::mapToEstimate).collect(Collectors.toList());

	}

	@Override
	protected List<HotelEvent> findAll() {
		return hotelEventRepo.findAll();
	}

	@Override
	protected List<HotelEvent> findByTour(Integer tourId) {
		return hotelEventRepo.findByTourId(tourId);
	}

	@Override
	protected HotelEvent findById(Integer id) {
		return hotelEventRepo.findById(id).orElse(null);
	}

	@Override
	protected HotelEvent save(HotelEvent hotelEvent) {
		HotelEvent result = hotelEventRepo.save(hotelEvent);
		tourService.calculate(result.getTour().getId());
		return result;
	}

	@Override
	protected boolean deleteById(Integer id) {
		HotelEvent result = findById(id);
		if (result == null){
			return false;
		}
		hotelEventRepo.deleteById(id);
		tourService.calculate(result.getTour().getId());
		return !hotelEventRepo.existsById(id);
	}

	@Override
	protected HotelEventDto mapToDto(HotelEvent entity) {
		HotelEventDto dto = new HotelEventDto();
		dto.setId(entity.getId());
		dto.setEventDate(entity.getDate());
		dto.setPax(entity.getPax());
		dto.setPrice(entity.getPrice());
		dto.setTourId(entity.getTour().getId());
		dto.setService(hotelServService.findListDtoById(entity.getService().getId()));
		dto.setTotal(entity.getPrice().multiply(BigDecimal.valueOf(entity.getPax())));
		dto.setNettoPrice(entity.getNettoPrice());
		dto.setCreationDate(entity.getCreationDate());
		dto.setNettoTotal(entity.getNettoPrice().multiply(BigDecimal.valueOf(entity.getPax())));
		return dto;
	}

	@Override
	protected HotelEvent mapToEntity(HotelEventDto dto) {
		HotelEvent event = new HotelEvent();
		event.setId(dto.getId());
		event.setDate(dto.getEventDate());
		event.setPax(dto.getPax());
		event.setPrice(dto.getPrice());
		event.setTour(tourService.findById(dto.getTourId()));
		event.setService(hotelServService.findById(dto.getService().getId()));
		event.setNettoPrice(dto.getNettoPrice());
		event.setCreationDate(dto.getCreationDate());
		return event;
	}

	private HotelEventEstimate mapToEstimate (HotelEvent hotelEvent) {
		HotelEventEstimate estimate = new HotelEventEstimate();
		estimate.setEventDate(hotelEvent.getDate());
		estimate.setPax(hotelEvent.getPax());
		estimate.setPrice(hotelEvent.getPrice());
		estimate.setService(hotelServService.findListDtoById(hotelEvent.getService().getId()));
		estimate.setHotel(hotelService.findListDtoById(hotelServService.findParentId(hotelEvent.getService().getId())));
		return estimate;
	}

}
