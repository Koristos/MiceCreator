package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.models.basic.Country;
import ru.geekbrains.micecreator.models.complex.Tour;
import ru.geekbrains.micecreator.repository.TourRepo;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Data
public class TourService {

	@Autowired
	private TourRepo tourRepo;

	public List<Tour> findAll() {
		return tourRepo.findAll();
	}

	public Tour findById(Integer id) {
		return tourRepo.findById(id).orElse(null);
	}

	public List<Tour> findByDates(Date firstDate, Date secondDate) {
		return tourRepo.findByStartDateBetween(firstDate, secondDate);
	}

	public List<Tour> findByDatesAndCountry(Date firstDate, Date secondDate, Country country) {
		return tourRepo.findByStartDateBetweenAndCountry(firstDate, secondDate, country);
	}

	public List<Tour> findByDatesAndCountry(Date firstDate, Date secondDate, Integer countryId) {
		return tourRepo.findByStartDateBetweenAndCountryId(firstDate, secondDate, countryId);
	}

	public Tour addNew(Tour newTour) {
		return tourRepo.save(newTour);
	}

	public Tour edit(Tour tour) {
		return tourRepo.save(tour);
	}

	public boolean deleteById(Integer id) {
		tourRepo.deleteById(id);
		return !tourRepo.existsById(id);
	}

}
