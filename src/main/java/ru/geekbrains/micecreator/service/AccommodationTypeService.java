package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.basic.full.AccommTypeDto;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.basic.list.SimpleTypes;
import ru.geekbrains.micecreator.models.basic.AccommodationType;
import ru.geekbrains.micecreator.repository.AccommodationTypeRepo;
import ru.geekbrains.micecreator.service.prototypes.SimpleTypeService;

import java.util.List;

@Service
@AllArgsConstructor
public class AccommodationTypeService extends SimpleTypeService<AccommTypeDto, AccommodationType> {

	@Autowired
	private final AccommodationTypeRepo accommodationTypeRepo;
	private final SimpleTypes simpleType = SimpleTypes.ACCOMM_TYPE;

	@Override
	protected List<AccommodationType> findAll() {
		return accommodationTypeRepo.findAll();
	}

	@Override
	protected List<AccommodationType> findByNamePart(String namePart) {
		return accommodationTypeRepo.findByNameStartingWith(namePart);
	}

	@Override
	protected AccommodationType findById(Integer id) {
		return accommodationTypeRepo.findById(id).orElse(null);
	}

	@Override
	protected AccommodationType save(AccommodationType newType) {
		return accommodationTypeRepo.save(newType);
	}

	@Override
	protected boolean deleteById(Integer id) {
		accommodationTypeRepo.deleteById(id);
		return !accommodationTypeRepo.existsById(id);
	}

	@Override
	protected AccommTypeDto mapToDto(AccommodationType entity) {
		AccommTypeDto dto = new AccommTypeDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setPaxNumber(entity.getPaxNumber());
		return dto;
	}

	@Override
	protected ListItemDto mapToListItemDto(AccommodationType item) {
		ListItemDto dto = new ListItemDto();
		dto.setId(item.getId());
		dto.setName(item.getName());
		dto.setItemType(simpleType);
		return dto;
	}

	@Override
	protected AccommodationType mapToEntity(AccommTypeDto dto) {
		AccommodationType entity = new AccommodationType();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setPaxNumber(dto.getPaxNumber());
		return entity;
	}

}
