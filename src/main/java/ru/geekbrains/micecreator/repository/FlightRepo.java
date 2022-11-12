package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.geekbrains.micecreator.models.complex.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepo extends JpaRepository<Flight, Integer> {

	List<Flight> findByTourId(Integer tourId);

	List<Flight> findByDepartureAirportIdAndArrivalAirportIdAndDepartureDateBetweenAndCreationDateBetween(Integer departureAirportId, Integer arrivalAirportId,
	                                                                                                      LocalDateTime firstDate, LocalDateTime secondDate,
	                                                                                                      LocalDate firstCreationDate, LocalDate secondCreationDate);

	List<Flight> findByDepartureAirportIdAndArrivalAirportIdAndAirlineIdAndDepartureDateBetweenAndCreationDateBetween(Integer departureAirportId, Integer arrivalAirportId,
	                                                                                                                  Integer airlineId, LocalDateTime firstDate, LocalDateTime secondDate,
	                                                                                                                  LocalDate firstCreationDate, LocalDate secondCreationDate);
}
