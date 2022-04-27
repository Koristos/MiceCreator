package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.complex.Flight;
import ru.geekbrains.micecreator.models.complex.Tour;

import java.util.Date;
import java.util.List;

public interface FlightRepo extends JpaRepository<Flight, Integer> {

	List<Flight> findByTour(Tour tour);

	List<Flight> findByTourId(Integer tourId);

	List<Flight> findByDepartureAirportIdAndArrivalAirportIdAndDepartureDateBetween(Integer departureAirportId, Integer arrivalAirportId, Date firstDate, Date secondDate);

	List<Flight> findByDepartureAirportIdAndArrivalAirportIdAndAirlineIdAndDepartureDateBetween(Integer departureAirportId, Integer arrivalAirportId, Integer airlineId, Date firstDate, Date secondDate);
}
