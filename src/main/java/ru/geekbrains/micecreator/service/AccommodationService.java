package ru.geekbrains.micecreator.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.models.complex.Accommodation;
import ru.geekbrains.micecreator.models.complex.Tour;
import ru.geekbrains.micecreator.repository.AccommodationRepo;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class AccommodationService {

	@Autowired
	private AccommodationRepo accommodationRepo;

	public List<Accommodation> findAll() {
		return accommodationRepo.findAll();
	}

	public Accommodation findById(Integer id) {
		return accommodationRepo.findById(id).orElse(null);
	}

	public List<Accommodation> findByTour(Tour tour) {
		return accommodationRepo.findByTour(tour);
	}

	public List<Accommodation> findByTour(Integer tourId) {
		return accommodationRepo.findByTourId(tourId);
	}

	public List<Accommodation> findByRoomAccTypeIdsInDates(Integer roomId, Integer accTypeId, Date firstDate, Date secondDate) {
		return accommodationRepo.findByRoomIdAndAccTypeIdAndCheckInDateBetween(roomId, accTypeId, firstDate, secondDate);
	}

	public Accommodation addNew(Accommodation accommodation) {
		return accommodationRepo.save(accommodation);
	}

	public Accommodation edit(Accommodation accommodation) {
		return accommodationRepo.save(accommodation);
	}

	public boolean deleteById(Integer id) {
		accommodationRepo.deleteById(id);
		return !accommodationRepo.existsById(id);
	}

}
