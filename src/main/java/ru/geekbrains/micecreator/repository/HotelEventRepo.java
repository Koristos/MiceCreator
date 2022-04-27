package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.complex.HotelEvent;
import ru.geekbrains.micecreator.models.complex.Tour;

import java.util.Date;
import java.util.List;

public interface HotelEventRepo extends JpaRepository<HotelEvent, Integer> {

	List<HotelEvent> findByTour(Tour tour);

	List<HotelEvent> findByTourId(Integer tourId);

	List<HotelEvent> findByServiceIdAndDateBetween(Integer serviceId, Date firstDate, Date secondDate);
}
