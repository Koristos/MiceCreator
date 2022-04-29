package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.basic.Hotel;

import java.util.List;

public interface HotelRepo extends JpaRepository<Hotel, Integer> {

	List<Hotel> findByNameStartingWith(String namePart);

	List<Hotel> findByLocationId(Integer locationId);

	List<Hotel> findByLocationIdAndNameStartingWith(Integer locationId, String namePart);

	List<Hotel> findByLocation_RegionId(Integer regionId);

	List<Hotel> findByLocation_RegionIdAndNameStartingWith(Integer regionId, String namePart);

	List<Hotel> findByLocation_Region_CountryId(Integer countryId);

	List<Hotel> findByLocation_Region_CountryIdAndNameStartingWith(Integer countryId, String namePart);


}
