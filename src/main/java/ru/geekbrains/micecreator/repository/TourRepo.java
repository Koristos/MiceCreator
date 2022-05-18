package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.complex.Tour;

import java.time.LocalDate;
import java.util.List;

public interface TourRepo extends JpaRepository<Tour, Integer> {

	List<Tour> findByStartDateBetween(LocalDate firstDate, LocalDate secondDate);

	List<Tour> findByStartDateBetweenAndCountryId(LocalDate firstDate, LocalDate secondDate, Integer countryId);
}
