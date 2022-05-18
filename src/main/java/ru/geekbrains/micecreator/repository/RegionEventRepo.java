package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.complex.RegionEvent;

import java.time.LocalDate;
import java.util.List;

public interface RegionEventRepo extends JpaRepository<RegionEvent, Integer> {

	List<RegionEvent> findByTourId(Integer tourId);

	List<RegionEvent> findByServiceIdAndDateBetween(Integer serviceId, LocalDate firstDate, LocalDate secondDate);
}
