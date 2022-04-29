package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.basic.full.RegionServiceDto;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.basic.list.SimpleTypes;
import ru.geekbrains.micecreator.models.basic.RegionServ;
import ru.geekbrains.micecreator.repository.RegionServRepo;
import ru.geekbrains.micecreator.service.prototypes.SimpleTypeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RegionServService extends SimpleTypeService<RegionServiceDto, RegionServ> {

	@Autowired
	private RegionServRepo regionServRepo;
	@Autowired
	private final RegionService regionService;
	private final SimpleTypes simpleType = SimpleTypes.REGION_SERVICE;


	public List<ListItemDto> findLocationByRegionId(Integer regionId) {
		return findByRegion(regionId).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	public List<ListItemDto> findLocationByRegionIdAndNamePart(Integer regionId, String namePart) {
		return findByRegionAndNamePart(regionId, namePart).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	protected List<RegionServ> findByRegion(Integer regionId) {
		checkInputId(regionId);
		return regionServRepo.findByRegionId(regionId);
	}

	protected List<RegionServ> findByRegionAndNamePart(Integer regionId, String namePart) {
		checkInputId(regionId);
		return regionServRepo.findByRegionIdAndNameStartsWith(regionId, nameToStandard(namePart));
	}

	@Override
	protected List<RegionServ> findAll() {
		return regionServRepo.findAll();
	}

	@Override
	protected List<RegionServ> findByNamePart(String namePart) {
		return regionServRepo.findByNameStartingWith(namePart);
	}

	@Override
	protected RegionServ findById(Integer id) {
		return regionServRepo.findById(id).orElse(null);
	}

	@Override
	protected RegionServ save(RegionServ service) {
		return regionServRepo.save(service);
	}

	@Override
	protected boolean deleteById(Integer id) {
		regionServRepo.deleteById(id);
		return !regionServRepo.existsById(id);
	}

	@Override
	protected RegionServiceDto mapToDto(RegionServ entity) {
		RegionServiceDto dto = new RegionServiceDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		dto.setRegionId(entity.getRegion().getId());
		return dto;
	}

	@Override
	protected ListItemDto mapToListItemDto(RegionServ entity) {
		ListItemDto dto = new ListItemDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setItemType(simpleType);
		return dto;
	}

	@Override
	protected RegionServ mapToEntity(RegionServiceDto dto) {
		RegionServ entity = new RegionServ();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setRegion(regionService.findById(dto.getRegionId()));
		return entity;
	}
}
