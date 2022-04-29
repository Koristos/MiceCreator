package ru.geekbrains.micecreator.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.dto.basic.full.AirlineDto;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.basic.list.SimpleTypes;
import ru.geekbrains.micecreator.models.basic.Airline;
import ru.geekbrains.micecreator.repository.AirlineRepo;
import ru.geekbrains.micecreator.service.prototypes.SimpleTypeService;

import java.util.List;

@Service
@AllArgsConstructor
public class AirlineService extends SimpleTypeService<AirlineDto, Airline> {

	@Autowired
	private final AirlineRepo airlineRepo;
	private final SimpleTypes simpleType = SimpleTypes.AIRLINE;

	@Override
	protected List<Airline> findAll() {
		return airlineRepo.findAll();
	}

	@Override
	protected List<Airline> findByNamePart(String namePart) {
		return airlineRepo.findByNameStartingWith(namePart);
	}

	@Override
	protected Airline findById(Integer id) {
		return airlineRepo.findById(id).orElse(null);
	}

	@Override
	protected Airline save(Airline entity) {
		return airlineRepo.save(entity);
	}

	@Override
	protected boolean deleteById(Integer id) {
		airlineRepo.deleteById(id);
		return !airlineRepo.existsById(id);
	}

	@Override
	protected AirlineDto mapToDto(Airline entity) {
		AirlineDto dto = new AirlineDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		return dto;
	}

	@Override
	protected ListItemDto mapToListItemDto(Airline entity) {
		ListItemDto dto = new ListItemDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setItemType(simpleType);
		return dto;
	}

	@Override
	protected Airline mapToEntity(AirlineDto dto) {
		Airline entity = new Airline();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		return entity;
	}

}
