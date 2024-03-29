package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.basic.SearchParams;
import ru.geekbrains.micecreator.dto.basic.full.LocationDto;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.basic.list.SimpleTypes;
import ru.geekbrains.micecreator.models.basic.Location;
import ru.geekbrains.micecreator.repository.LocationRepo;
import ru.geekbrains.micecreator.service.prototypes.SimpleTypeService;
import ru.geekbrains.micecreator.utils.AppUtils;
import ru.geekbrains.micecreator.utils.PathUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LocationService extends SimpleTypeService<LocationDto, Location> {

	@Autowired
	private final LocationRepo locationRepo;
	@Autowired
	private final RegionService regionService;
	@Autowired
	private final PathUtils pathUtils;
	private final SimpleTypes simpleType = SimpleTypes.LOCATION;

	/**
	 * Метод ищет локации по параметрам:
	 * - по id Региона, если указан
	 * - по части имени, если указано
	 * - если параметры пустые, ищет без ограничений
	 * @param params параметры для поиска
	 * @return список Локаций в виде DTO
	 */
	@Override
	public List<ListItemDto> findBySearchParams(SearchParams params) {
		if (params.getRegionId() != null && !AppUtils.isBlank(params.getNamePart())){
			return findLocationByRegionIdAndNamePart(params.getRegionId(), params.getNamePart());
		}else if (params.getRegionId() != null) {
			return findLocationByRegionId(params.getRegionId());
		}else {
			return super.findBySearchParams(params);
		}
	}

	protected List<ListItemDto> findLocationByRegionId(Integer regionId) {
		checkInputId(regionId);
		return findByRegion(regionId).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	protected List<ListItemDto> findLocationByRegionIdAndNamePart(Integer regionId, String namePart) {
		checkInputId(regionId);
		return findByRegionAndNamePart(regionId, namePart).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	public Integer findParentId (Integer id) {
		return findById(id).getRegion().getId();
	}

	protected List<Location> findByRegion(Integer regionId) {
		return locationRepo.findByRegionId(regionId);
	}

	protected List<Location> findByRegionAndNamePart(Integer regionId, String namePart) {
		return locationRepo.findByRegionIdAndNameStartsWith(regionId, nameToStandard(namePart));
	}

	@Override
	protected List<Location> findAll() {
		return locationRepo.findAll();
	}

	@Override
	protected List<Location> findByNamePart(String namePart) {
		return locationRepo.findByNameStartingWith(namePart);
	}

	@Override
	protected Location findById(Integer id) {
		return locationRepo.findById(id).orElse(null);
	}

	@Override
	protected Location save(Location location) {
		return locationRepo.save(location);
	}

	@Override
	protected boolean deleteById(Integer id) {
		locationRepo.deleteById(id);
		return !locationRepo.existsById(id);
	}

	@Override
	protected LocationDto mapToDto(Location entity) {
		LocationDto dto = new LocationDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setRegionId(entity.getRegion().getId());
		dto.setImageOne(entity.getImageOne());
		return dto;
	}

	@Override
	protected ListItemDto mapToListItemDto(Location entity) {
		ListItemDto dto = new ListItemDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setItemType(simpleType);
		return dto;
	}

	@Override
	protected Location mapToEntity(LocationDto dto) {
		Location entity = new Location();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setRegion(regionService.findById(dto.getRegionId()));
		if (AppUtils.isBlank(dto.getImageOne()) || dto.getId() == null || !isImageExist(dto.getId(), 1)) {
			entity.setImageOne(null);
		} else {
			entity.setImageOne(AppUtils.createImageName("location", dto.getId(), 1));
		}
		return entity;
	}

	private boolean isImageExist(Integer entityId, Integer imageNum) {
		return pathUtils.isImageExist(AppUtils.createImageName("location", entityId, imageNum));
	}

}
