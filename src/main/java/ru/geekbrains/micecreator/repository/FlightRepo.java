package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.complex.Flight;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepo extends JpaRepository<Flight, Integer> {

	List<Flight> findByTourId(Integer tourId);

	List<Flight> findByDepartureAirportIdAndArrivalAirportIdAndDepartureDateBetween(Integer departureAirportId, Integer arrivalAirportId, LocalDateTime firstDate, LocalDateTime secondDate);

	List<Flight> findByDepartureAirportIdAndArrivalAirportIdAndAirlineIdAndDepartureDateBetween(Integer departureAirportId, Integer arrivalAirportId, Integer airlineId, LocalDateTime firstDate, LocalDateTime secondDate);
}
