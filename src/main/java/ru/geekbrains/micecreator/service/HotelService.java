package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.models.basic.Country;
import ru.geekbrains.micecreator.models.basic.Hotel;
import ru.geekbrains.micecreator.models.basic.Location;
import ru.geekbrains.micecreator.models.basic.Region;
import ru.geekbrains.micecreator.repository.HotelRepo;

import java.util.List;

@Service
@AllArgsConstructor
@Data
public class HotelService {

	@Autowired
	private HotelRepo hotelRepo;

	public List<Hotel> findAll() {
		return hotelRepo.findAll();
	}

	public Hotel findById(Integer id) {
		return hotelRepo.findById(id).orElse(null);
	}

	public List<Hotel> findByNamePart(String namePart) {
		return hotelRepo.findByNameStartingWith(namePart);
	}

	public List<Hotel> findByCountry(Country country) {
		return hotelRepo.findByLocation_Region_Country(country);
	}

	public List<Hotel> findByCountry(Integer countryId) {
		return hotelRepo.findByLocation_Region_CountryId(countryId);
	}

	public List<Hotel> findByRegion(Region region) {
		return hotelRepo.findByLocation_Region(region);
	}

	public List<Hotel> findByRegion(Integer regionId) {
		return hotelRepo.findByLocation_RegionId(regionId);
	}

	public List<Hotel> findByLocation(Location location) {
		return hotelRepo.findByLocation(location);
	}

	public List<Hotel> findByLocation(Integer locationId) {
		return hotelRepo.findByLocationId(locationId);
	}

	public List<Hotel> findByCountryAndNamePart(Country country, String namePart) {
		return hotelRepo.findByLocation_Region_CountryAndNameStartingWith(country, namePart);
	}

	public List<Hotel> findByCountryAndNamePart(Integer countryId, String namePart) {
		return hotelRepo.findByLocation_Region_CountryIdAndNameStartingWith(countryId, namePart);
	}

	public List<Hotel> findByRegionAndNamePart(Region region, String namePart) {
		return hotelRepo.findByLocation_RegionAndNameStartingWith(region, namePart);
	}

	public List<Hotel> findByRegionAndNamePart(Integer regionId, String namePart) {
		return hotelRepo.findByLocation_RegionIdAndNameStartingWith(regionId, namePart);
	}

	public List<Hotel> findByLocationAndNamePart(Location location, String namePart) {
		return hotelRepo.findByLocationAndNameStartingWith(location, namePart);
	}

	public List<Hotel> findByLocationAndNamePart(Integer locationId, String namePart) {
		return hotelRepo.findByLocationIdAndNameStartingWith(locationId, namePart);
	}

	public Hotel addNew(Hotel newHotel) {
		return hotelRepo.save(newHotel);
	}

	public Hotel edit(Hotel hotel) {
		return hotelRepo.save(hotel);
	}

	public boolean deleteById(Integer id) {
		hotelRepo.deleteById(id);
		return !hotelRepo.existsById(id);
	}

}
