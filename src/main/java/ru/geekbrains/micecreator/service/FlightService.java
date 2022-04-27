package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.models.complex.Flight;
import ru.geekbrains.micecreator.models.complex.Tour;
import ru.geekbrains.micecreator.repository.FlightRepo;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Data
public class FlightService {

	@Autowired
	private FlightRepo flightRepo;

	public List<Flight> findAll() {
		return flightRepo.findAll();
	}

	public Flight findById(Integer id) {
		return flightRepo.findById(id).orElse(null);
	}

	public List<Flight> findByTour(Tour tour) {
		return flightRepo.findByTour(tour);
	}

	public List<Flight> findByTour(Integer tourId) {
		return flightRepo.findByTourId(tourId);
	}

	public List<Flight> findByAirportsIdsAndDates(Integer departureAirportId, Integer arrivalAirportId, Date firstDate, Date secondDate) {
		return flightRepo.findByDepartureAirportIdAndArrivalAirportIdAndDepartureDateBetween(departureAirportId, arrivalAirportId, firstDate, secondDate);
	}

	public List<Flight> findByAirportsAirlineIdsAndDates(Integer departureAirportId, Integer arrivalAirportId, Integer airlineId, Date firstDate, Date secondDate) {
		return flightRepo.findByDepartureAirportIdAndArrivalAirportIdAndAirlineIdAndDepartureDateBetween(departureAirportId, arrivalAirportId, airlineId, firstDate, secondDate);
	}

	public Flight addNew(Flight flight) {
		return flightRepo.save(flight);
	}

	public Flight edit(Flight flight) {
		return flightRepo.save(flight);
	}

	public boolean deleteById(Integer id) {
		flightRepo.deleteById(id);
		return !flightRepo.existsById(id);
	}

}
