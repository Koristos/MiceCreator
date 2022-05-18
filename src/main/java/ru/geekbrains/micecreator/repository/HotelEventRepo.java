package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.complex.HotelEvent;

import java.time.LocalDate;
import java.util.List;

public interface HotelEventRepo extends JpaRepository<HotelEvent, Integer> {

	List<HotelEvent> findByTourId(Integer tourId);

	List<HotelEvent> findByServiceIdAndDateBetween(Integer serviceId, LocalDate firstDate, LocalDate secondDate);
}
