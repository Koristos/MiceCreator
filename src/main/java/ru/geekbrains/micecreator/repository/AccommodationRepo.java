package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.geekbrains.micecreator.models.complex.Accommodation;
import ru.geekbrains.micecreator.models.complex.Tour;

import java.time.LocalDate;
import java.util.List;

public interface AccommodationRepo extends JpaRepository<Accommodation, Integer> {

	List<Accommodation> findByTour(Tour tour);

	List<Accommodation> findByTourId(Integer tourId);

	List<Accommodation> findByRoomIdAndAccTypeIdAndCheckInDateBetweenAndCreationDateBetween(Integer roomId, Integer accTypeId, LocalDate firstDate, LocalDate secondDate,
	                                                                                        LocalDate firstCreationDate, LocalDate secondCreationDate);

	List<Accommodation> findByRoom_HotelIdAndAccTypeIdAndCheckInDateBetweenAndCreationDateBetween(Integer hotelId, Integer accTypeId, LocalDate firstDate, LocalDate secondDate,
	                                                                                              LocalDate firstCreationDate, LocalDate secondCreationDate);
}
