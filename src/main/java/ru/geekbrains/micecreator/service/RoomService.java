package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.models.basic.Hotel;
import ru.geekbrains.micecreator.models.basic.Room;
import ru.geekbrains.micecreator.repository.RoomRepo;

import java.util.List;

@Service
@AllArgsConstructor
@Data
public class RoomService {

	@Autowired
	private RoomRepo roomRepo;

	public List<Room> findAll() {
		return roomRepo.findAll();
	}

	public Room findById(Integer id) {
		return roomRepo.findById(id).orElse(null);
	}

	public List<Room> findByNamePart(String namePart) {
		return roomRepo.findByNameStartingWith(namePart);
	}

	public List<Room> findByHotel(Hotel hotel) {
		return roomRepo.findByHotel(hotel);
	}

	public List<Room> findByHotel(Integer hotelId) {
		return roomRepo.findByHotelId(hotelId);
	}

	public List<Room> findByHotelAndNamePart(Hotel hotel, String namePart) {
		return roomRepo.findByHotelAndNameStartingWith(hotel, namePart);
	}

	public List<Room> findByHotelAndNamePart(Integer hotelId, String namePart) {
		return roomRepo.findByHotelIdAndNameStartsWith(hotelId, namePart);
	}

	public Room addNew(Room newRoom) {
		return roomRepo.save(newRoom);
	}

	public Room edit(Room room) {
		return roomRepo.save(room);
	}

	public boolean deleteById(Integer id) {
		roomRepo.deleteById(id);
		return !roomRepo.existsById(id);
	}
}
