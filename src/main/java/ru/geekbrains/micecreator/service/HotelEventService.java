package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.models.complex.HotelEvent;
import ru.geekbrains.micecreator.models.complex.Tour;
import ru.geekbrains.micecreator.repository.HotelEventRepo;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Data
public class HotelEventService {

	@Autowired
	private HotelEventRepo hotelEventRepo;

	public List<HotelEvent> findAll() {
		return hotelEventRepo.findAll();
	}

	public HotelEvent findById(Integer id) {
		return hotelEventRepo.findById(id).orElse(null);
	}

	public List<HotelEvent> findByTour(Tour tour) {
		return hotelEventRepo.findByTour(tour);
	}

	public List<HotelEvent> findByTour(Integer tourId) {
		return hotelEventRepo.findByTourId(tourId);
	}

	public List<HotelEvent> findByAirportsIdsInDates(Integer hotelServId, Date firstDate, Date secondDate) {
		return hotelEventRepo.findByServiceIdAndDateBetween(hotelServId, firstDate, secondDate);
	}

	public HotelEvent addNew(HotelEvent hotelEvent) {
		return hotelEventRepo.save(hotelEvent);
	}

	public HotelEvent edit(HotelEvent hotelEvent) {
		return hotelEventRepo.save(hotelEvent);
	}

	public boolean deleteById(Integer id) {
		hotelEventRepo.deleteById(id);
		return !hotelEventRepo.existsById(id);
	}

}
