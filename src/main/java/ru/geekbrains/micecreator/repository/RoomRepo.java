package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.basic.Hotel;
import ru.geekbrains.micecreator.models.basic.Room;

import java.util.List;

public interface RoomRepo extends JpaRepository<Room, Integer> {

	List<Room> findByNameStartingWith(String namePart);

	List<Room> findByHotel(Hotel hotel);

	List<Room> findByHotelId(Integer hotelId);

	List<Room> findByHotelAndNameStartingWith(Hotel hotel, String namePart);

	List<Room> findByHotelIdAndNameStartsWith(Integer hotelId, String namePart);
}
