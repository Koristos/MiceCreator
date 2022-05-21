package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.complex.ComplexParams;
import ru.geekbrains.micecreator.dto.complex.FlightDto;
import ru.geekbrains.micecreator.dto.complex.estimate.FlightEstimate;
import ru.geekbrains.micecreator.models.complex.Flight;
import ru.geekbrains.micecreator.repository.FlightRepo;
import ru.geekbrains.micecreator.service.prototypes.ComplexTypeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FlightService extends ComplexTypeService<FlightDto, Flight> {

	@Autowired
	private final FlightRepo flightRepo;
	@Autowired
	private final AirportService airportService;
	@Autowired
	private final AirlineService airlineService;
	@Autowired
	private final TourService tourService;

	public List<FlightDto> findByParams(ComplexParams params) {
		if (params.getAirlineId() == null){
			return findDtoByPointsAndDates(params.getDepartureAirportId(), params.getArrivalAirportId(), params.getFirstDate(), params.getSecondDate());
		}else {
			return findDtoByPointsAndAirlineAndDates(params.getDepartureAirportId(), params.getArrivalAirportId(), params.getAirlineId(),
					params.getFirstDate(), params.getSecondDate());
		}
	}

	public List<FlightDto> findDtoByPointsAndDates(Integer departureAirportId, Integer arrivalAirportId, LocalDate firstDate, LocalDate secondDate) {
		checkInput(departureAirportId, arrivalAirportId, firstDate, secondDate);
		checkDates(firstDate, secondDate);
		return findByAirportsIdsAndDates(departureAirportId, arrivalAirportId, firstDate, secondDate).stream()
				.map(this::mapToDto).collect(Collectors.toList());
	}

	public List<FlightDto> findDtoByPointsAndAirlineAndDates(Integer departureAirportId, Integer arrivalAirportId, Integer airlineId, LocalDate firstDate, LocalDate secondDate) {
		checkInput(departureAirportId, arrivalAirportId, airlineId, firstDate, secondDate);
		checkDates(firstDate, secondDate);
		return findByAirportsAirlineIdsAndDates(departureAirportId, arrivalAirportId, airlineId, firstDate, secondDate).stream()
				.map(this::mapToDto).collect(Collectors.toList());
	}

	public List<FlightEstimate> makeEstimate(Integer tourId) {
		return findByTour (tourId).stream().map(this::mapToEstimate).collect(Collectors.toList());

	}


	protected List<Flight> findByAirportsIdsAndDates(Integer departureAirportId, Integer arrivalAirportId, LocalDate firstDate, LocalDate secondDate) {
		return flightRepo.findByDepartureAirportIdAndArrivalAirportIdAndDepartureDateBetween(departureAirportId, arrivalAirportId, firstDate.atStartOfDay(), secondDate.atStartOfDay());
	}

	protected List<Flight> findByAirportsAirlineIdsAndDates(Integer departureAirportId, Integer arrivalAirportId, Integer airlineId, LocalDate firstDate, LocalDate secondDate) {
		return flightRepo.findByDepartureAirportIdAndArrivalAirportIdAndAirlineIdAndDepartureDateBetween(departureAirportId, arrivalAirportId, airlineId, firstDate.atStartOfDay(), secondDate.atStartOfDay());
	}

	@Override
	protected List<Flight> findAll() {
		return flightRepo.findAll();
	}

	@Override
	protected List<Flight> findByTour(Integer tourId) {
		return flightRepo.findByTourId(tourId);
	}

	@Override
	protected Flight findById(Integer id) {
		return flightRepo.findById(id).orElse(null);
	}

	@Override
	protected Flight save(Flight flight) {
		Flight result = flightRepo.save(flight);
		tourService.calculate(result.getTour().getId());
		return result;
	}

	@Override
	protected boolean deleteById(Integer id) {
		Flight result = findById(id);
		if (result == null){
			return false;
		}
		tourService.calculate(result.getTour().getId());
		flightRepo.deleteById(id);
		return !flightRepo.existsById(id);
	}

	@Override
	protected FlightDto mapToDto(Flight entity) {
		FlightDto dto = new FlightDto();
		dto.setId(entity.getId());
		dto.setDepartureDate(entity.getDepartureDate());
		dto.setArrivalDate(entity.getArrivalDate());
		dto.setPax(entity.getPax());
		dto.setPrice(entity.getPrice());
		dto.setTourId(entity.getTour().getId());
		dto.setAirline(airlineService.findListDtoById(entity.getAirline().getId()));
		dto.setDepartureAirport(airportService.findListDtoById(entity.getDepartureAirport().getId()));
		dto.setArrivalAirport(airportService.findListDtoById(entity.getArrivalAirport().getId()));
		dto.setTotal(entity.getPrice().multiply(BigDecimal.valueOf(entity.getPax())));
		return dto;
	}

	@Override
	protected Flight mapToEntity(FlightDto dto) {
		Flight flight = new Flight();
		flight.setId(dto.getId());
		flight.setDepartureDate(dto.getDepartureDate());
		flight.setArrivalDate(dto.getArrivalDate());
		flight.setPax(dto.getPax());
		flight.setPrice(dto.getPrice());
		flight.setTour(tourService.findById(dto.getTourId()));
		flight.setAirline(airlineService.findById(dto.getAirline().getId()));
		flight.setDepartureAirport(airportService.findById(dto.getDepartureAirport().getId()));
		flight.setArrivalAirport(airportService.findById(dto.getArrivalAirport().getId()));
		return flight;
	}

	private FlightEstimate mapToEstimate (Flight flight) {
		FlightEstimate estimate = new FlightEstimate();
		estimate.setDepartureDate(flight.getDepartureDate());
		estimate.setArrivalDate(flight.getArrivalDate());
		estimate.setPax(flight.getPax());
		estimate.setPrice(flight.getPrice());
		estimate.setAirline(airlineService.findListDtoById(flight.getAirline().getId()));
		estimate.setDepartureAirport(airportService.findListDtoById(flight.getDepartureAirport().getId()));
		estimate.setArrivalAirport(airportService.findListDtoById(flight.getArrivalAirport().getId()));
		return estimate;
	}

}
