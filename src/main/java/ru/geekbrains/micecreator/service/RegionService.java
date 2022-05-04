package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.basic.SearchParams;
import ru.geekbrains.micecreator.dto.basic.full.RegionDto;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.basic.list.SimpleTypes;
import ru.geekbrains.micecreator.models.basic.Region;
import ru.geekbrains.micecreator.repository.RegionRepo;
import ru.geekbrains.micecreator.service.prototypes.SimpleTypeService;
import ru.geekbrains.micecreator.utils.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class RegionService extends SimpleTypeService<RegionDto, Region> {

	@Autowired
	private final RegionRepo regionRepo;
	@Autowired
	private final CountryService countryService;
	private final SimpleTypes simpleType = SimpleTypes.REGION;


	@Override
	public List<ListItemDto> findBySearchParams(SearchParams params) {
		if (params.getCountryId() != null && !StringUtils.isBlank(params.getNamePart())){
			return findRegionByCountryIdAndNamePart(params.getCountryId(), params.getNamePart());
		}else if (params.getCountryId() != null) {
			return findRegionByCountryId(params.getCountryId());
		}else {
			return super.findBySearchParams(params);
		}
	}

	public List<ListItemDto> findRegionByCountryId(Integer countryId) {
		checkInputId(countryId);
		return findByCountry(countryId).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	public List<ListItemDto> findRegionByCountryIdAndNamePart(Integer countryId, String namePart) {
		return findByCountryAndNamePart(countryId, namePart).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	protected List<Region> findByCountry(Integer countryId) {
		return regionRepo.findByCountryId(countryId);
	}

	protected List<Region> findByCountryAndNamePart(Integer countryId, String namePart) {
		return regionRepo.findByCountryIdAndNameStartsWith(countryId, nameToStandard(namePart));
	}

	@Override
	protected List<Region> findAll() {
		return regionRepo.findAll();
	}

	@Override
	protected Region findById(Integer id) {
		return regionRepo.findById(id).orElse(null);
	}

	@Override
	protected List<Region> findByNamePart(String namePart) {
		return regionRepo.findByNameStartingWith(namePart);
	}

	@Override
	protected Region save(Region newRegion) {
		return regionRepo.save(newRegion);
	}

	@Override
	protected boolean deleteById(Integer id) {
		regionRepo.deleteById(id);
		return !regionRepo.existsById(id);
	}

	@Override
	protected RegionDto mapToDto(Region entity) {
		RegionDto dto = new RegionDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setCountryId(entity.getCountry().getId());
		return dto;
	}

	@Override
	protected ListItemDto mapToListItemDto(Region entity) {
		ListItemDto dto = new ListItemDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setItemType(simpleType);
		return dto;
	}

	@Override
	protected Region mapToEntity(RegionDto dto) {
		Region entity = new Region();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setCountry(countryService.findById(dto.getCountryId()));
		return entity;
	}

}
