package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.micecreator.models.basic.HotelServ;
import ru.geekbrains.micecreator.models.basic.Location;

import java.util.List;

public interface HotelServRepo extends JpaRepository<HotelServ, Integer> {

	@Query("select h from HotelServ h where h.name LIKE :namePart")
	List<HotelServ> findByNameStartingWith(@Param("namePart")String namePart);

	List<HotelServ> findByHotelId(Integer hotelId);

	@Query("select h from HotelServ h where h.name LIKE :namePart and h.hotel.id = :hotelId")
	List<HotelServ> findByHotelIdAndNameStartsWith(@Param("hotelId")Integer hotelId, @Param("namePart")String namePart);
}
