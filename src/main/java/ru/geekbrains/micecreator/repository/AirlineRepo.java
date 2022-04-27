package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.basic.Airline;

import java.util.List;

public interface AirlineRepo extends JpaRepository<Airline, Integer> {

	List<Airline> findByNameStartingWith(String namePart);
}
