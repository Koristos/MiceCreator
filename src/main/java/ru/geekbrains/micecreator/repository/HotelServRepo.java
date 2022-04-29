package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.basic.HotelServ;

import java.util.List;

public interface HotelServRepo extends JpaRepository<HotelServ, Integer> {

	List<HotelServ> findByNameStartingWith(String namePart);

	List<HotelServ> findByHotelId(Integer hotelId);

	List<HotelServ> findByHotelIdAndNameStartsWith(Integer hotelId, String namePart);
}
