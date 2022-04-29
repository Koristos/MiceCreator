package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.complex.HotelEventDto;
import ru.geekbrains.micecreator.models.complex.HotelEvent;
import ru.geekbrains.micecreator.repository.HotelEventRepo;
import ru.geekbrains.micecreator.service.prototypes.ComplexTypeService;

import java.util.Date;
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


	public List<HotelEventDto> findDtoByServiceIdInDates(Integer hotelServId, Date firstDate, Date secondDate) {
		checkInput(hotelServId, firstDate, secondDate);
		checkDates(firstDate, secondDate);
		return findByServiceIdInDates(hotelServId, firstDate, secondDate).stream()
				.map(this::mapToDto).collect(Collectors.toList());
	}

	protected List<HotelEvent> findByServiceIdInDates(Integer hotelServId, Date firstDate, Date secondDate) {
		return hotelEventRepo.findByServiceIdAndDateBetween(hotelServId, firstDate, secondDate);
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
		tourService.calculate(result.getTour().getId());
		hotelEventRepo.deleteById(id);
		return !hotelEventRepo.existsById(id);
	}

	@Override
	protected HotelEventDto mapToDto(HotelEvent entity) {
		HotelEventDto dto = new HotelEventDto();
		dto.setId(entity.getId());
		dto.setDate(entity.getDate());
		dto.setPax(entity.getPax());
		dto.setPrice(entity.getPrice());
		dto.setTourId(entity.getTour().getId());
		dto.setService(hotelServService.findListDtoById(entity.getService().getId()));
		return dto;
	}

	@Override
	protected HotelEvent mapToEntity(HotelEventDto dto) {
		HotelEvent event = new HotelEvent();
		event.setId(dto.getId());
		event.setDate(dto.getDate());
		event.setPax(dto.getPax());
		event.setPrice(dto.getPrice());
		event.setTour(tourService.findById(dto.getTourId()));
		event.setService(hotelServService.findById(dto.getService().getId()));
		return event;
	}

}
