package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.micecreator.models.basic.Airport;

import java.util.List;

public interface AirportRepo extends JpaRepository<Airport, Integer> {

	@Query("select a from Airport a where a.name LIKE :namePart")
	List<Airport> findByNameStartingWith(@Param("namePart")String namePart);

	@Query("select a from Airport a where a.code LIKE :code")
	List<Airport> findByCodeStartingWith(@Param("code")String code);

	List<Airport> findByRegionId(Integer regionId);

	@Query("select a from Airport a where a.name LIKE :namePart and a.region.id = :regionId")
	List<Airport> findByRegionIdAndNameStartingWith(@Param("regionId")Integer regionId, @Param("namePart")String namePart);

	@Query("select a from Airport a where a.code LIKE :code and a.region.id = :regionId")
	List<Airport> findByRegionIdAndCodeStartingWith(@Param("regionId")Integer regionId, @Param("code")String code);
}
