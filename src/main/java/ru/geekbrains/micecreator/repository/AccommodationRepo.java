package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.complex.Accommodation;
import ru.geekbrains.micecreator.models.complex.Tour;

import java.util.Date;
import java.util.List;

public interface AccommodationRepo extends JpaRepository<Accommodation, Integer> {

	List<Accommodation> findByTour(Tour tour);

	List<Accommodation> findByTourId(Integer tourId);

	List<Accommodation> findByRoomIdAndAccTypeIdAndCheckInDateBetween(Integer roomId, Integer accTypeId, Date firstDate, Date secondDate);
}
