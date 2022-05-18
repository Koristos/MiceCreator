package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.basic.SearchParams;
import ru.geekbrains.micecreator.dto.basic.full.AirportDto;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.basic.list.SimpleTypes;
import ru.geekbrains.micecreator.models.basic.Airport;
import ru.geekbrains.micecreator.repository.AirportRepo;
import ru.geekbrains.micecreator.service.prototypes.SimpleTypeService;
import ru.geekbrains.micecreator.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AirportService extends SimpleTypeService<AirportDto, Airport> {

	@Autowired
	private final AirportRepo airportRepo;
	@Autowired
	private final RegionService regionService;
	private final SimpleTypes simpleType = SimpleTypes.AIRPORT;


	@Override
	public List<ListItemDto> findBySearchParams(SearchParams params) {
		if (params.getRegionId() != null && !AppUtils.isBlank(params.getNamePart()) && params.isUsingAlterNames()) {
			return findAirportByNameOrCodePartAndRegionId(params.getNamePart(), params.getRegionId());
		} else if (params.getRegionId() != null && !AppUtils.isBlank(params.getNamePart())) {
			return findAirportByNamePartAndRegionId(params.getNamePart(), params.getRegionId());
		} else if (params.getRegionId() != null) {
			return findAirportByRegionId(params.getRegionId());
		} else if (!AppUtils.isBlank(params.getNamePart()) && params.isUsingAlterNames()) {
			return findAirportByNameOrCodePart(params.getNamePart());
		} else {
			return super.findBySearchParams(params);
		}
	}

	public List<ListItemDto> findAirportByNameOrCodePartAndRegionId(String namePart,Integer regionId) {
		List<Airport> result = airportRepo.findByRegionIdAndNameStartingWith(regionId, nameToStandard(namePart));
		if (namePart.length()<=3) {
			result.addAll(airportRepo.findByRegionIdAndCodeStartingWith(regionId, nameToStandard(namePart)));
		}
		return result.stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	public List<ListItemDto> findAirportByNamePartAndRegionId(String namePart,Integer regionId) {
		List<Airport> result = airportRepo.findByRegionIdAndNameStartingWith(regionId, nameToStandard(namePart));
		return result.stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	public List<ListItemDto> findAirportByNameOrCodePart(String namePart) {
		List<ListItemDto> result = new ArrayList<>(findListDtoByNamePart(namePart));
		result.addAll(findByCodePart(namePart).stream().map(this::mapToListItemDto).collect(Collectors.toList()));
		return result;
	}

	public List<ListItemDto> findAirportByRegionId(Integer regionId) {
		checkInputId(regionId);
		return findByRegion(regionId).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}


	protected List<Airport> findByRegion(Integer regionId) {
		return airportRepo.findByRegionId(regionId);
	}

	protected List<Airport> findByCodePart(String codePart) {
		if (codePart.length() > 3) {
			return new ArrayList<>();
		}
		return airportRepo.findByCodeStartingWith(nameToStandard(codePart));
	}


	@Override
	protected List<Airport> findAll() {
		return airportRepo.findAll();
	}

	@Override
	protected List<Airport> findByNamePart(String namePart) {
		return airportRepo.findByNameStartingWith(namePart);
	}

	@Override
	protected Airport findById(Integer id) {
		return airportRepo.findById(id).orElse(null);
	}

	@Override
	protected Airport save(Airport newAirport) {
		return airportRepo.save(newAirport);
	}

	@Override
	protected boolean deleteById(Integer id) {
		airportRepo.deleteById(id);
		return !airportRepo.existsById(id);
	}

	@Override
	protected AirportDto mapToDto(Airport entity) {
		AirportDto dto = new AirportDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setCode(entity.getCode());
		dto.setRegionId(entity.getRegion().getId());
		return dto;
	}

	@Override
	protected ListItemDto mapToListItemDto(Airport entity) {
		ListItemDto dto = new ListItemDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setItemType(simpleType);
		return dto;
	}

	@Override
	protected Airport mapToEntity(AirportDto dto) {
		Airport entity = new Airport();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setCode(dto.getCode());
		entity.setRegion(regionService.findById(dto.getRegionId()));
		return entity;
	}

}
