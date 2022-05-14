package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.micecreator.models.basic.Hotel;

import java.util.List;

public interface HotelRepo extends JpaRepository<Hotel, Integer> {

	@Query("select h from Hotel h where h.name LIKE :namePart")
	List<Hotel> findByNameStartingWith(@Param("namePart")String namePart);

	List<Hotel> findByLocationId(Integer locationId);

	@Query("select h from Hotel h where h.name LIKE :namePart and h.location.id = :locationId")
	List<Hotel> findByLocationIdAndNameStartingWith(@Param("locationId")Integer locationId, @Param("namePart")String namePart);

	List<Hotel> findByLocation_RegionId(Integer regionId);

	@Query("select h from Hotel h where h.name LIKE :namePart and h.location.region.id = :regionId")
	List<Hotel> findByLocation_RegionIdAndNameStartingWith(@Param("regionId")Integer regionId, @Param("namePart")String namePart);

	List<Hotel> findByLocation_Region_CountryId(Integer countryId);

	@Query("select h from Hotel h where h.name LIKE :namePart and h.location.region.country.id = :countryId")
	List<Hotel> findByLocation_Region_CountryIdAndNameStartingWith(@Param("countryId")Integer countryId, @Param("namePart")String namePart);


}
