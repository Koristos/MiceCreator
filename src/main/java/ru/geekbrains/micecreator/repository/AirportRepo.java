package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.basic.Airport;

import java.util.List;

public interface AirportRepo extends JpaRepository<Airport, Integer> {

	List<Airport> findByNameStartingWith(String namePart);

	List<Airport> findByCodeStartingWith(String code);

	List<Airport> findByRegionId(Integer regionId);

	List<Airport> findByRegionIdAndNameStartingWith(Integer regionId, String namePart);

	List<Airport> findByRegionIdAndCodeStartingWith(Integer regionId, String code);
}
