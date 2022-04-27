package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.basic.Airport;
import ru.geekbrains.micecreator.models.basic.Region;

import java.util.List;

public interface AirportRepo extends JpaRepository<Airport, Integer> {

	List<Airport> findByNameStartingWith(String namePart);

	List<Airport> findByCode(String code);

	List<Airport> findByRegion(Region region);

	List<Airport> findByRegionId(Integer regionId);
}
