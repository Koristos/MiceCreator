package ru.geekbrains.micecreator.service.prototypes;

import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.basic.prototype.BasicDto;

import java.util.List;
import java.util.stream.Collectors;

public abstract class SimpleTypeService<D extends BasicDto, E> {

	public List<ListItemDto> findAllListDto() {
		return findAll().stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	public List<ListItemDto> findListDtoByNamePart(String namePart) {
		namePart = nameToStandard(namePart);
		return findByNamePart(namePart).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	public ListItemDto findListDtoById(Integer id) {
		checkInputId(id);
		E type = findById(id);

		if (type == null) {
			return null;
		}
		return mapToListItemDto(type);
	}

	public D findDtoById(Integer id) {
		checkInputId(id);
		E type = findById(id);

		if (type == null) {
			return null;
		}
		return mapToDto(type);
	}

	public D createEntity(D dto) {
		if (dto == null || dto.getId() != null) {
			throw new RuntimeException("invalid input!");
		}
		dto.setName(nameToStandard(dto.getName()));
		return mapToDto(save(mapToEntity(dto)));
	}

	public D editEntity(D dto) {
		if (dto == null || dto.getId() == null) {
			throw new RuntimeException("invalid input!");
		}
		dto.setName(nameToStandard(dto.getName()));
		return mapToDto(save(mapToEntity(dto)));
	}

	public boolean deleteEntity(Integer id) {
		if (id == null) {
			throw new RuntimeException("invalid input!");
		}
		return deleteById(id);
	}

	protected String nameToStandard(String name) {
		if (name.isBlank()) {
			throw new RuntimeException("invalid input!");
		}
		return name.toUpperCase();
	}

	protected void checkInputId(Integer id) {
		if (id == null) {
			throw new RuntimeException("Invalid Input!");
		}
	}

	protected abstract List<E> findAll();

	protected abstract List<E> findByNamePart(String namePart);

	protected abstract E findById(Integer id);

	protected abstract E save(E entity);

	protected abstract boolean deleteById(Integer id);

	protected abstract D mapToDto(E entity);

	protected abstract ListItemDto mapToListItemDto(E item);

	protected abstract E mapToEntity(D dto);
}
