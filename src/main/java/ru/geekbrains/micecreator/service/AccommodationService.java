package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.complex.AccommodationDto;
import ru.geekbrains.micecreator.dto.complex.ComplexParams;
import ru.geekbrains.micecreator.dto.complex.estimate.AccommodationEstimate;
import ru.geekbrains.micecreator.models.complex.Accommodation;
import ru.geekbrains.micecreator.repository.AccommodationRepo;
import ru.geekbrains.micecreator.service.prototypes.ComplexTypeService;
import ru.geekbrains.micecreator.utils.AppUtils;


import java.math.BigDecimal;
import java.time.LocalDate;
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
	@Autowired
	private final HotelService hotelService;

	/**
	 * Метод ищет Размещение по параметрам:
	 * - по id Номера, если указан
	 * - по ID отеля, если id номера не указан
	 * - в диапазоне между датами по дате начала размещения
	 * - в диапазоне между датами создания по дате создания
	 * @param params параметры для поиска
	 * @return список размещений в виде DTO
	 */
	public List<AccommodationDto> findByParams(ComplexParams params) {
		checkDates(params.getFirstDate(), params.getSecondDate());
		if (params.getRoomId() != null) {
			checkInput(params.getRoomId(), params.getAccTypeId(), params.getFirstDate(), params.getSecondDate(),
					params.getFirstDateOfCreation(), params.getSecondDateOfCreation());
			return findByRoomAccTypeIdsInDates(params.getRoomId(), params.getAccTypeId(), params.getFirstDate(), params.getSecondDate(),
					params.getFirstDateOfCreation(), params.getSecondDateOfCreation()).stream()
					.map(this::mapToDto).collect(Collectors.toList());
		} else {
			checkInput(params.getHotelId(), params.getAccTypeId(), params.getFirstDate(), params.getSecondDate(),
					params.getFirstDateOfCreation(), params.getSecondDateOfCreation());
			return findByHotelAccTypeIdsInDates(params.getHotelId(), params.getAccTypeId(), params.getFirstDate(), params.getSecondDate(),
					params.getFirstDateOfCreation(), params.getSecondDateOfCreation()).stream()
					.map(this::mapToDto).collect(Collectors.toList());
		}
	}

	/**
	 * Метод подгатавливает данные для создания сметы тура
	 * @param tourId id тура
	 * @return данные для сметы по всем размещениям тура
	 */
	public List<AccommodationEstimate> makeEstimate(Integer tourId) {
		return findByTour (tourId).stream().map(this::mapToEstimate).collect(Collectors.toList());

	}

	protected List<Accommodation> findByRoomAccTypeIdsInDates(Integer roomId, Integer accTypeId, LocalDate firstDate, LocalDate secondDate,
	                                                          LocalDate firstCreationDate, LocalDate secondCreationDate) {
		return accommodationRepo.findByRoomIdAndAccTypeIdAndCheckInDateBetweenAndCreationDateBetween(roomId, accTypeId, firstDate, secondDate,
				firstCreationDate, secondCreationDate);
	}

	protected List<Accommodation> findByHotelAccTypeIdsInDates(Integer hotelId, Integer accTypeId, LocalDate firstDate, LocalDate secondDate,
	                                                           LocalDate firstCreationDate, LocalDate secondCreationDate) {
		return accommodationRepo.findByRoom_HotelIdAndAccTypeIdAndCheckInDateBetweenAndCreationDateBetween(hotelId, accTypeId, firstDate, secondDate,
				firstCreationDate, secondCreationDate);
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
		accommodationRepo.deleteById(id);
		tourService.calculate(result.getTour().getId());
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
		dto.setHotel(hotelService.findListDtoById(roomService.findDtoById(entity.getRoom().getId()).getHotelId()));
		dto.setNights(AppUtils.countDaysDifference(entity.getCheckInDate(), entity.getCheckOutDate()));
		dto.setRoomCount((int) Math.ceil(((double)entity.getPax() / entity.getAccType().getPaxNumber())));
		dto.setTotal(entity.getPrice().multiply(BigDecimal.valueOf(entity.getPax())).multiply(BigDecimal.valueOf(dto.getNights())));
		dto.setCreationDate(entity.getCreationDate());
		dto.setNettoPrice(entity.getNettoPrice());
		dto.setNettoTotal(entity.getNettoPrice().multiply(BigDecimal.valueOf(entity.getPax())).multiply(BigDecimal.valueOf(dto.getNights())));
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
		entity.setNettoPrice(dto.getNettoPrice());
		entity.setCreationDate(dto.getCreationDate());
		return entity;
	}

	private AccommodationEstimate mapToEstimate (Accommodation accommodation) {
		AccommodationEstimate estimate = new AccommodationEstimate();
		estimate.setCheckInDate(accommodation.getCheckInDate());
		estimate.setCheckOutDate(accommodation.getCheckOutDate());
		estimate.setPax(accommodation.getPax());
		estimate.setPrice(accommodation.getPrice());
		estimate.setRoom(roomService.findListDtoById(accommodation.getRoom().getId()));
		estimate.setAccType(accommodationTypeService.findListDtoById(accommodation.getAccType().getId()));
		estimate.setHotel(hotelService.findListDtoById(roomService.findDtoById(accommodation.getRoom().getId()).getHotelId()));
		estimate.setNights(AppUtils.countDaysDifference(accommodation.getCheckInDate(), accommodation.getCheckOutDate()));
		estimate.setRoomCount((int) Math.ceil(((double)accommodation.getPax() / accommodation.getAccType().getPaxNumber())));
		return estimate;
	}

}
