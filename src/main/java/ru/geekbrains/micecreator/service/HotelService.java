package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.basic.SearchParams;
import ru.geekbrains.micecreator.dto.basic.full.HotelDto;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.basic.list.SimpleTypes;
import ru.geekbrains.micecreator.models.basic.Hotel;
import ru.geekbrains.micecreator.repository.HotelRepo;
import ru.geekbrains.micecreator.service.prototypes.SimpleTypeService;
import ru.geekbrains.micecreator.utils.AppUtils;
import ru.geekbrains.micecreator.utils.PathUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HotelService extends SimpleTypeService<HotelDto, Hotel> {

	@Autowired
	private final HotelRepo hotelRepo;
	@Autowired
	private final LocationService locationService;
	@Autowired
	private final PathUtils pathUtils;
	private final SimpleTypes simpleType = SimpleTypes.HOTEL;

	/**
	 * Метод ищет Отели по параметрам:
	 * - по id Локации, если указан
	 * - по части имени, если указано
	 * - по id Региона, если указан
	 * - по id Страны, если указан
	 * - если параметры пустые, ищет без ограничений
	 * @param params параметры для поиска
	 * @return список отелей в виде DTO
	 */
	@Override
	public List<ListItemDto> findBySearchParams(SearchParams params) {
		if (params.getLocationId() != null && !AppUtils.isBlank(params.getNamePart())) {
			return findHotelByLocationAndNamePart(params.getLocationId(), params.getNamePart());
		} else if (params.getLocationId() != null) {
			return findHotelByLocation(params.getLocationId());
		} else if (params.getRegionId() != null && !AppUtils.isBlank(params.getNamePart())) {
			return findHotelByRegionAndNamePart(params.getRegionId(), params.getNamePart());
		} else if (params.getRegionId() != null) {
			return findHotelByRegion(params.getRegionId());
		} else if (params.getCountryId() != null && !AppUtils.isBlank(params.getNamePart())) {
			return findHotelByCountryAndNamePart(params.getCountryId(), params.getNamePart());
		} else if (params.getCountryId() != null) {
			return findHotelByCountry(params.getCountryId());
		} else {
			return super.findBySearchParams(params);
		}
	}

	protected List<ListItemDto> findHotelByCountry(Integer countryId) {
		checkInputId(countryId);
		return findByCountry(countryId).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	protected List<ListItemDto> findHotelByRegion(Integer regionId) {
		checkInputId(regionId);
		return findByRegion(regionId).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	protected List<ListItemDto> findHotelByLocation(Integer locationId) {
		checkInputId(locationId);
		return findByLocation(locationId).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	protected List<ListItemDto> findHotelByCountryAndNamePart(Integer countryId, String namePart) {
		checkInputId(countryId);
		return findByCountryAndNamePart(countryId, namePart).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	protected List<ListItemDto> findHotelByRegionAndNamePart(Integer regionId, String namePart) {
		checkInputId(regionId);
		return findByRegionAndNamePart(regionId, namePart).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	protected List<ListItemDto> findHotelByLocationAndNamePart(Integer locationId, String namePart) {
		checkInputId(locationId);
		return findByLocationAndNamePart(locationId, namePart).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	public Integer findParentId (Integer id) {
		return findById(id).getLocation().getId();
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
		dto.setImageOne(entity.getImageOne());
		dto.setImageTwo(entity.getImageTwo());
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
		if (AppUtils.isBlank(dto.getImageOne()) || dto.getId() == null || !isImageExist(dto.getId(), 1)) {
			entity.setImageOne(null);
		} else {
			entity.setImageOne(AppUtils.createImageName("hotel", dto.getId(), 1));
		}
		if (AppUtils.isBlank(dto.getImageTwo()) || dto.getId() == null || !isImageExist(dto.getId(), 2)) {
			entity.setImageTwo(null);
		} else {
			entity.setImageTwo(AppUtils.createImageName("hotel", dto.getId(), 2));
		}
		return entity;
	}

	private boolean isImageExist(Integer entityId, Integer imageNum) {
		return pathUtils.isImageExist(AppUtils.createImageName("hotel", entityId, imageNum));
	}

}
