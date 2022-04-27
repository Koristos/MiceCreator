package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.basic.Hotel;
import ru.geekbrains.micecreator.models.basic.HotelServ;

import java.util.List;

public interface HotelServRepo extends JpaRepository<HotelServ, Integer> {

	List<HotelServ> findByNameStartingWith(String namePart);

	List<HotelServ> findByHotel(Hotel hotel);

	List<HotelServ> findByHotelId(Integer hotelId);

	List<HotelServ> findByHotelAndNameStartingWith(Hotel hotel, String namePart);

	List<HotelServ> findByHotelIdAndNameStartsWith(Integer hotelId, String namePart);
}
