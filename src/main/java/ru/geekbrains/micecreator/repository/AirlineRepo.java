package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.micecreator.models.basic.Airline;

import java.util.List;

public interface AirlineRepo extends JpaRepository<Airline, Integer> {

	@Query("select a from Airline a where a.name LIKE :namePart")
	List<Airline> findByNameStartingWith(@Param("namePart")String namePart);
}
