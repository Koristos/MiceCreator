package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.basic.full.HotelDto;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.basic.list.SimpleTypes;
import ru.geekbrains.micecreator.models.basic.Hotel;
import ru.geekbrains.micecreator.repository.HotelRepo;
import ru.geekbrains.micecreator.service.prototypes.SimpleTypeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HotelService extends SimpleTypeService<HotelDto, Hotel> {

	@Autowired
	private final HotelRepo hotelRepo;
	@Autowired
	private final LocationService locationService;
	private final SimpleTypes simpleType = SimpleTypes.HOTEL;


	public List<ListItemDto> findHotelByCountry(Integer countryId) {
		checkInputId(countryId);
		return findByCountry(countryId).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	public List<ListItemDto> findHotelByRegion(Integer regionId) {
		checkInputId(regionId);
		return findByRegion(regionId).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	public List<ListItemDto> findHotelByLocation(Integer locationId) {
		checkInputId(locationId);
		return findByLocation(locationId).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	public List<ListItemDto> findHotelByCountryAndNamePart(Integer countryId, String namePart) {
		checkInputId(countryId);
		return findByCountryAndNamePart(countryId, namePart).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	public List<ListItemDto> findHotelByRegionAndNamePart(Integer regionId, String namePart) {
		checkInputId(regionId);
		return findByRegionAndNamePart(regionId, namePart).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	public List<ListItemDto> findHotelByLocationAndNamePart(Integer locationId, String namePart) {
		checkInputId(locationId);
		return findByLocationAndNamePart(locationId, namePart).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}


	protected List<Hotel> findByCountry(Integer countryId) {
		return hotelRepo.findByLocation_Region_CountryId(countryId);
	}

	protected List<Hotel> findByRegion(Integer regionId) {
		return hotelRepo.findByLocation_RegionId(regionId);
	}

	protected List<Hotel> findByLocation(Integer locationId) {
		return hotelRepo.findByLocationId(locationId);
	}

	protected List<Hotel> findByCountryAndNamePart(Integer countryId, String namePart) {
		return hotelRepo.findByLocation_Region_CountryIdAndNameStartingWith(countryId, nameToStandard(namePart));
	}

	protected List<Hotel> findByRegionAndNamePart(Integer regionId, String namePart) {
		return hotelRepo.findByLocation_RegionIdAndNameStartingWith(regionId, nameToStandard(namePart));
	}

	protected List<Hotel> findByLocationAndNamePart(Integer locationId, String namePart) {
		return hotelRepo.findByLocationIdAndNameStartingWith(locationId, nameToStandard(namePart));
	}

	@Override
	protected List<Hotel> findAll() {
		return hotelRepo.findAll();
	}

	@Override
	protected Hotel findById(Integer id) {
		return hotelRepo.findById(id).orElse(null);
	}

	@Override
	protected List<Hotel> findByNamePart(String namePart) {
		return hotelRepo.findByNameStartingWith(namePart);
	}

	@Override
	protected Hotel save(Hotel hotel) {
		return hotelRepo.save(hotel);
	}

	@Override
	protected boolean deleteById(Integer id) {
		hotelRepo.deleteById(id);
		return !hotelRepo.existsById(id);
	}

	@Override
	protected HotelDto mapToDto(Hotel entity) {
		HotelDto dto = new HotelDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setLocationId(entity.getLocation().getId());
		return dto;
	}

	@Override
	protected ListItemDto mapToListItemDto(Hotel entity) {
		ListItemDto dto = new ListItemDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setItemType(simpleType);
		return dto;
	}

	@Override
	protected Hotel mapToEntity(HotelDto dto) {
		Hotel entity = new Hotel();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setLocation(locationService.findById(dto.getLocationId()));
		return entity;
	}

}
