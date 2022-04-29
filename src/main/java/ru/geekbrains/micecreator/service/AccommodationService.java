package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.complex.AccommodationDto;
import ru.geekbrains.micecreator.models.complex.Accommodation;
import ru.geekbrains.micecreator.repository.AccommodationRepo;
import ru.geekbrains.micecreator.service.prototypes.ComplexTypeService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccommodationService extends ComplexTypeService<AccommodationDto, Accommodation> {

	@Autowired
	private final AccommodationRepo accommodationRepo;
	@Autowired
	private final RoomService roomService;
	@Autowired
	private final AccommodationTypeService accommodationTypeService;
	@Autowired
	private final TourService tourService;

	public List<AccommodationDto> findByParams(Integer roomId, Integer accTypeId, Date firstDate, Date secondDate) {
		checkInput(roomId, accTypeId, firstDate, secondDate);
		checkDates(firstDate, secondDate);
		return findByRoomAccTypeIdsInDates(roomId, accTypeId, firstDate, secondDate).stream().map(this::mapToDto).collect(Collectors.toList());
	}

	protected List<Accommodation> findByRoomAccTypeIdsInDates(Integer roomId, Integer accTypeId, Date firstDate, Date secondDate) {
		return accommodationRepo.findByRoomIdAndAccTypeIdAndCheckInDateBetween(roomId, accTypeId, firstDate, secondDate);
	}

	@Override
	protected List<Accommodation> findAll() {
		return accommodationRepo.findAll();
	}

	@Override
	protected List<Accommodation> findByTour(Integer tourId) {
		return accommodationRepo.findByTourId(tourId);
	}

	@Override
	protected Accommodation findById(Integer id) {
		return accommodationRepo.findById(id).orElse(null);
	}

	@Override
	protected Accommodation save(Accommodation accommodation) {
		Accommodation result = accommodationRepo.save(accommodation);
		tourService.calculate(result.getTour().getId());
		return result;
	}

	@Override
	protected boolean deleteById(Integer id) {
		Accommodation result = findById(id);
		if (result == null){
			return false;
		}
		tourService.calculate(result.getTour().getId());
		accommodationRepo.deleteById(id);
		return !accommodationRepo.existsById(id);
	}

	@Override
	protected AccommodationDto mapToDto(Accommodation entity) {
		AccommodationDto dto = new AccommodationDto();
		dto.setId(entity.getId());
		dto.setCheckInDate(entity.getCheckInDate());
		dto.setCheckOutDate(entity.getCheckOutDate());
		dto.setPax(entity.getPax());
		dto.setPrice(entity.getPrice());
		dto.setTourId(entity.getTour().getId());
		dto.setRoom(roomService.findListDtoById(entity.getRoom().getId()));
		dto.setAccType(accommodationTypeService.findListDtoById(entity.getAccType().getId()));
		return dto;
	}

	@Override
	protected Accommodation mapToEntity(AccommodationDto dto) {
		Accommodation entity = new Accommodation();
		entity.setId(dto.getId());
		entity.setCheckInDate(dto.getCheckInDate());
		entity.setCheckOutDate(dto.getCheckOutDate());
		entity.setPax(dto.getPax());
		entity.setPrice(dto.getPrice());
		entity.setTour(tourService.findById(dto.getTourId()));
		entity.setRoom(roomService.findById(dto.getRoom().getId()));
		entity.setAccType(accommodationTypeService.findById(dto.getAccType().getId()));
		return entity;
	}

}
