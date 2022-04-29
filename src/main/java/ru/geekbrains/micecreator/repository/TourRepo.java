package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.complex.Tour;

import java.util.Date;
import java.util.List;

public interface TourRepo extends JpaRepository<Tour, Integer> {

	List<Tour> findByStartDateBetween(Date firstDate, Date secondDate);

	List<Tour> findByStartDateBetweenAndCountryId(Date firstDate, Date secondDate, Integer countryId);
}
