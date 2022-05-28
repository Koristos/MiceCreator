package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.basic.SearchParams;
import ru.geekbrains.micecreator.dto.basic.full.RegionServiceDto;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.basic.list.SimpleTypes;
import ru.geekbrains.micecreator.models.basic.RegionServ;
import ru.geekbrains.micecreator.repository.RegionServRepo;
import ru.geekbrains.micecreator.service.prototypes.SimpleTypeService;
import ru.geekbrains.micecreator.utils.AppUtils;

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

	@Override
	public List<ListItemDto> findBySearchParams(SearchParams params) {
		if (params.getRegionId() != null && !AppUtils.isBlank(params.getNamePart())){
			return findRegServByRegionIdAndNamePart(params.getRegionId(), params.getNamePart());
		}else if (params.getRegionId() != null) {
			return findRegServByRegionId(params.getRegionId());
		}else {
			return super.findBySearchParams(params);
		}
	}

	public List<ListItemDto> findRegServByRegionId(Integer regionId) {
		return findByRegion(regionId).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	public List<ListItemDto> findRegServByRegionIdAndNamePart(Integer regionId, String namePart) {
		return findByRegionAndNamePart(regionId, namePart).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	public Integer findParentId (Integer id) {
		return findById(id).getRegion().getId();
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
		dto.setImageOne(entity.getImageOne());
		dto.setImageTwo(entity.getImageTwo());
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
		if (AppUtils.isBlank(dto.getImageOne()) || dto.getId() == null || !isImageExist(dto.getId(), 1)) {
			entity.setImageOne(null);
		} else {
			entity.setImageOne(AppUtils.createImageName("region_service", dto.getId(), 1));
		}
		if (AppUtils.isBlank(dto.getImageTwo()) || dto.getId() == null || !isImageExist(dto.getId(), 2)) {
			entity.setImageTwo(null);
		} else {
			entity.setImageTwo(AppUtils.createImageName("region_service", dto.getId(), 2));
		}
		return entity;
	}

	private boolean isImageExist(Integer entityId, Integer imageNum) {
		return AppUtils.isImageExist(AppUtils.createImageName("region_service", entityId, imageNum));
	}
}
