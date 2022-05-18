package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.complex.Accommodation;
import ru.geekbrains.micecreator.models.complex.Tour;

import java.time.LocalDate;
import java.util.List;

public interface AccommodationRepo extends JpaRepository<Accommodation, Integer> {

	List<Accommodation> findByTour(Tour tour);

	List<Accommodation> findByTourId(Integer tourId);

	List<Accommodation> findByRoomIdAndAccTypeIdAndCheckInDateBetween(Integer roomId, Integer accTypeId, LocalDate firstDate, LocalDate secondDate);

	List<Accommodation> findByRoom_HotelIdAndAccTypeIdAndCheckInDateBetween(Integer hotelId, Integer accTypeId, LocalDate firstDate, LocalDate secondDate);
}
