package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.complex.RegionEvent;
import ru.geekbrains.micecreator.models.complex.Tour;

import java.util.Date;
import java.util.List;

public interface RegionEventRepo extends JpaRepository<RegionEvent, Integer> {

	List<RegionEvent> findByTour(Tour tour);

	List<RegionEvent> findByTourId(Integer tourId);

	List<RegionEvent> findByServiceIdAndDateBetween(Integer serviceId, Date firstDate, Date secondDate);
}
