package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.complex.TourDto;
import ru.geekbrains.micecreator.models.complex.Accommodation;
import ru.geekbrains.micecreator.models.complex.Flight;
import ru.geekbrains.micecreator.models.complex.HotelEvent;
import ru.geekbrains.micecreator.models.complex.RegionEvent;
import ru.geekbrains.micecreator.models.complex.Tour;
import ru.geekbrains.micecreator.repository.TourRepo;
import ru.geekbrains.micecreator.service.prototypes.ComplexTypeService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TourService extends ComplexTypeService<TourDto, Tour> {

	@Autowired
	private final TourRepo tourRepo;
	@Autowired
	private final CountryService countryService;

	public List<TourDto> findDtoToursByDatesAndCountry(Date firstDate, Date secondDate, Integer countryId) {
		checkInput(firstDate, secondDate, countryId);
		checkDates(firstDate, secondDate);
		return findByDatesAndCountry(firstDate, secondDate, countryId).stream().map(this::mapToDto).collect(Collectors.toList());
	}

	public List<TourDto> findDtoToursByDates(Date firstDate, Date secondDate) {
		checkInput(firstDate, secondDate);
		checkDates(firstDate, secondDate);
		return findByDates(firstDate, secondDate).stream().map(this::mapToDto).collect(Collectors.toList());
	}

	protected List<Tour> findByDatesAndCountry(Date firstDate, Date secondDate, Integer countryId) {
		return tourRepo.findByStartDateBetweenAndCountryId(firstDate, secondDate, countryId);
	}

	protected List<Tour> findByDates(Date firstDate, Date secondDate) {
		return tourRepo.findByStartDateBetween(firstDate, secondDate);
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
		return entity;
	}


	protected Tour calculate(Integer tourId) {
		Tour tour = findById(tourId);
		BigDecimal total = BigDecimal.ZERO;

		for (Accommodation item : tour.getAccommodations()) {
			total = total.add(BigDecimal.valueOf(item.getPax()).multiply(item.getPrice()));
		}

		for (Flight item : tour.getFlights()) {
			total = total.add(BigDecimal.valueOf(item.getPax()).multiply(item.getPrice()));
		}

		for (RegionEvent item : tour.getRegionEvents()) {
			total = total.add(BigDecimal.valueOf(item.getPax()).multiply(item.getPrice()));
		}

		for (HotelEvent item : tour.getHotelEvents()) {
			total = total.add(BigDecimal.valueOf(item.getPax()).multiply(item.getPrice()));
		}

		tour.setTotalPrice(total);
		return save(tour);
	}


}
