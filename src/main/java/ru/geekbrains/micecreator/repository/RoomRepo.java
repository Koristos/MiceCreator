package ru.geekbrains.micecreator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.micecreator.models.basic.Room;

import java.util.List;

public interface RoomRepo extends JpaRepository<Room, Integer> {

	@Query("select r from Room r where r.name LIKE :namePart")
	List<Room> findByNameStartingWith(@Param("namePart")String namePart);

	List<Room> findByHotelId(Integer hotelId);

	@Query("select r from Room r where r.name LIKE :namePart and r.hotel.id = :hotelId")
	List<Room> findByHotelIdAndNameStartsWith(@Param("hotelId")Integer hotelId, @Param("namePart")String namePart);
}
