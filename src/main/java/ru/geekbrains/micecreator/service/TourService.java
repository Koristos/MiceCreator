package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.complex.ComplexParams;
import ru.geekbrains.micecreator.dto.complex.TourDto;
import ru.geekbrains.micecreator.exceptions.BadInputException;
import ru.geekbrains.micecreator.models.complex.Accommodation;
import ru.geekbrains.micecreator.models.complex.Flight;
import ru.geekbrains.micecreator.models.complex.HotelEvent;
import ru.geekbrains.micecreator.models.complex.RegionEvent;
import ru.geekbrains.micecreator.models.complex.Tour;
import ru.geekbrains.micecreator.models.currency.Course;
import ru.geekbrains.micecreator.models.security.User;
import ru.geekbrains.micecreator.repository.TourRepo;
import ru.geekbrains.micecreator.service.currency.CbCurrencyService;
import ru.geekbrains.micecreator.service.currency.CourseService;
import ru.geekbrains.micecreator.service.prototypes.ComplexTypeService;
import ru.geekbrains.micecreator.service.security.SecurityService;
import ru.geekbrains.micecreator.utils.AppUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TourService extends ComplexTypeService<TourDto, Tour> {

	@Autowired
	private final TourRepo tourRepo;
	@Autowired
	private final CountryService countryService;
	@Autowired
	private final CourseService courseService;
	@Autowired
	private final CbCurrencyService cbCurrencyService;
	@Autowired
	private final SecurityService securityService;

	/**
	 * Метод ищет Туры по параметрам:
	 * - по имени менеджера-создателя, если указан
	 * - в диапазоне между датами по дате начала тура
	 * @param params параметры для поиска
	 * @return список туров в виде DTO
	 */
	public List<TourDto> findByParams(ComplexParams params) {
		if (params.getCountryId() == null) {
			return findDtoToursByDates(params.getFirstDate(), params.getSecondDate(), params.getUserName());
		} else {
			return findDtoToursByDatesAndCountry(params.getFirstDate(), params.getSecondDate(), params.getCountryId(), params.getUserName());
		}
	}

	public List<TourDto> findDtoToursByDatesAndCountry(LocalDate firstDate, LocalDate secondDate, Integer countryId, String username) {
		checkInput(firstDate, secondDate, countryId);
		checkDates(firstDate, secondDate);
		if (StringUtils.isBlank(username)) {
			return findByDatesAndCountry(firstDate, secondDate, countryId).stream().map(this::mapToDto).collect(Collectors.toList());
		} else {
			return findByDatesAndCountryAndManager(firstDate, secondDate, countryId, username).stream().map(this::mapToDto).collect(Collectors.toList());
		}
	}

	public List<TourDto> findDtoToursByDates(LocalDate firstDate, LocalDate secondDate, String username) {
		checkInput(firstDate, secondDate);
		checkDates(firstDate, secondDate);
		if (StringUtils.isBlank(username)) {
			return findByDates(firstDate, secondDate).stream().map(this::mapToDto).collect(Collectors.toList());
		} else {
			return findByDatesAndManager(firstDate, secondDate, username).stream().map(this::mapToDto).collect(Collectors.toList());
		}
	}

	protected List<Tour> findByDatesAndCountry(LocalDate firstDate, LocalDate secondDate, Integer countryId) {
		return tourRepo.findByStartDateBetweenAndCountryId(firstDate, secondDate, countryId);
	}

	protected List<Tour> findByDatesAndCountryAndManager(LocalDate firstDate, LocalDate secondDate, Integer countryId, String userName) {
		return tourRepo.findByStartDateBetweenAndCountryIdAndUserManager(firstDate, secondDate, countryId, userName);
	}

	protected List<Tour> findByDates(LocalDate firstDate, LocalDate secondDate) {
		return tourRepo.findByStartDateBetween(firstDate, secondDate);
	}

	protected List<Tour> findByDatesAndManager(LocalDate firstDate, LocalDate secondDate, String userName) {
		return tourRepo.findByStartDateBetweenAndUserManager(firstDate, secondDate, userName);
	}


	@Override
	protected List<Tour> findAll() {
		return tourRepo.findAll();
	}

	@Override
	protected List<Tour> findByTour(Integer tourId) {
		List<Tour> result = new ArrayList<>();
		result.add(findById(tourId));
		return result;
	}

	@Override
	protected Tour findById(Integer id) {
		return tourRepo.findById(id).orElse(null);
	}

	@Override
	protected Tour save(Tour tour) {
		return tourRepo.save(tour);
	}

	@Override
	protected boolean deleteById(Integer id) {
		tourRepo.deleteById(id);
		return !tourRepo.existsById(id);
	}

	@Override
	protected TourDto mapToDto(Tour entity) {
		TourDto dto = new TourDto();
		dto.setId(entity.getId());
		dto.setPax(entity.getPax());
		dto.setStartDate(entity.getStartDate());
		dto.setEndDate(entity.getEndDate());
		dto.setTotalPrice(entity.getTotalPrice());
		dto.setCountry(countryService.findListDtoById(entity.getCountry().getId()));
		String currencyName = countryService.findById(entity.getCountry().getId()).getCurrency().getName();
		dto.setTourCurrency(currencyName);
		Course course = courseService.getCourse(currencyName);
		if (course == null) {
			cbCurrencyService.updateCurrencyInfo();
			course = courseService.getCourse(currencyName);
		}
		dto.setTotalPriceInBasicCurrency(course.getRate().multiply(entity.getTotalPrice())
				.setScale(2, RoundingMode.HALF_UP));
		dto.setNettoTotal(entity.getNettoTotal());
		dto.setCreationDate(entity.getCreationDate());
		dto.setUserName(entity.getUser().getManager());
		return dto;
	}

	@Override
	protected Tour mapToEntity(TourDto dto) {
		Tour entity = new Tour();
		entity.setId(dto.getId());
		entity.setPax(dto.getPax());
		entity.setStartDate(dto.getStartDate());
		entity.setEndDate(dto.getEndDate());
		entity.setTotalPrice(dto.getTotalPrice());
		entity.setCountry(countryService.findById(dto.getCountry().getId()));
		entity.setNettoTotal(dto.getNettoTotal());
		User user = securityService.findByManager(dto.getUserName()).orElse(null);
		if (user == null) {
			throw new BadInputException("Менеджер с указанным именем не найден");
		}
		entity.setUser(user);
		entity.setCreationDate(dto.getCreationDate());
		return entity;
	}


	protected Tour calculate(Integer tourId) {
		Tour tour = findById(tourId);
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal nettoTotal = BigDecimal.ZERO;

		for (Accommodation item : tour.getAccommodations()) {
			long nights = AppUtils.countDaysDifference(item.getCheckInDate(), item.getCheckOutDate());
			total = total.add(BigDecimal.valueOf(item.getPax()).multiply(item.getPrice()).multiply(BigDecimal.valueOf(nights)));
			nettoTotal = nettoTotal.add(BigDecimal.valueOf(item.getPax()).multiply(item.getNettoPrice()).multiply(BigDecimal.valueOf(nights)));
		}

		for (Flight item : tour.getFlights()) {
			total = total.add(BigDecimal.valueOf(item.getPax()).multiply(item.getPrice()));
			nettoTotal = nettoTotal.add(BigDecimal.valueOf(item.getPax()).multiply(item.getNettoPrice()));
		}

		for (RegionEvent item : tour.getRegionEvents()) {
			total = total.add(BigDecimal.valueOf(item.getPax()).multiply(item.getPrice()));
			nettoTotal = nettoTotal.add(BigDecimal.valueOf(item.getPax()).multiply(item.getNettoPrice()));
		}

		for (HotelEvent item : tour.getHotelEvents()) {
			total = total.add(BigDecimal.valueOf(item.getPax()).multiply(item.getPrice()));
			nettoTotal = nettoTotal.add(BigDecimal.valueOf(item.getPax()).multiply(item.getNettoPrice()));
		}

		tour.setTotalPrice(total);
		tour.setNettoTotal(nettoTotal);
		return save(tour);
	}


}
