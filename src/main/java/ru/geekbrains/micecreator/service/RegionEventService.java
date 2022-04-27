package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.models.complex.RegionEvent;
import ru.geekbrains.micecreator.models.complex.Tour;
import ru.geekbrains.micecreator.repository.RegionEventRepo;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Data
public class RegionEventService {

	@Autowired
	private RegionEventRepo regionEventRepo;

	public List<RegionEvent> findAll() {
		return regionEventRepo.findAll();
	}

	public RegionEvent findById(Integer id) {
		return regionEventRepo.findById(id).orElse(null);
	}

	public List<RegionEvent> findByTour(Tour tour) {
		return regionEventRepo.findByTour(tour);
	}

	public List<RegionEvent> findByTour(Integer tourId) {
		return regionEventRepo.findByTourId(tourId);
	}

	public List<RegionEvent> findByAirportsIdsInDates(Integer regionServId, Date firstDate, Date secondDate) {
		return regionEventRepo.findByServiceIdAndDateBetween(regionServId, firstDate, secondDate);
	}

	public RegionEvent addNew(RegionEvent regionEvent) {
		return regionEventRepo.save(regionEvent);
	}

	public RegionEvent edit(RegionEvent regionEvent) {
		return regionEventRepo.save(regionEvent);
	}

	public boolean deleteById(Integer id) {
		regionEventRepo.deleteById(id);
		return !regionEventRepo.existsById(id);
	}

}
