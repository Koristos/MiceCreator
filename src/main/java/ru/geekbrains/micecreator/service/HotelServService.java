package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.models.basic.Hotel;
import ru.geekbrains.micecreator.models.basic.HotelServ;
import ru.geekbrains.micecreator.repository.HotelServRepo;

import java.util.List;

@Service
@AllArgsConstructor
@Data
public class HotelServService {

	@Autowired
	private HotelServRepo hotelServRepo;

	public List<HotelServ> findAll() {
		return hotelServRepo.findAll();
	}

	public HotelServ findById(Integer id) {
		return hotelServRepo.findById(id).orElse(null);
	}

	public List<HotelServ> findByNamePart(String namePart) {
		return hotelServRepo.findByNameStartingWith(namePart);
	}

	public List<HotelServ> findByHotel(Hotel hotel) {
		return hotelServRepo.findByHotel(hotel);
	}

	public List<HotelServ> findByHotel(Integer hotelId) {
		return hotelServRepo.findByHotelId(hotelId);
	}

	public List<HotelServ> findByHotelAndNamePart(Hotel hotel, String namePart) {
		return hotelServRepo.findByHotelAndNameStartingWith(hotel, namePart);
	}

	public List<HotelServ> findByHotelAndNamePart(Integer hotelId, String namePart) {
		return hotelServRepo.findByHotelIdAndNameStartsWith(hotelId, namePart);
	}

	public HotelServ addNew(HotelServ newHotelServ) {
		return hotelServRepo.save(newHotelServ);
	}

	public HotelServ edit(HotelServ service) {
		return hotelServRepo.save(service);
	}

	public boolean deleteById(Integer id) {
		hotelServRepo.deleteById(id);
		return !hotelServRepo.existsById(id);
	}

}
