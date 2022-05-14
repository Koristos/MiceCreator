package ru.geekbrains.micecreator.service.prototypes;

import ru.geekbrains.micecreator.dto.basic.SearchParams;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.basic.prototype.BasicDto;
import ru.geekbrains.micecreator.exceptions.BadInputException;
import ru.geekbrains.micecreator.exceptions.DataNotFoundException;
import ru.geekbrains.micecreator.utils.StringUtils;

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
			throw new BadInputException("Invalid input for entity creation: Dto must be not null and have no id.");
		}
		dto.setName(nameToStandardForSave(dto.getName()));
		return mapToDto(save(mapToEntity(dto)));
	}

	public D editEntity(D dto) {
		if (dto == null || dto.getId() == null) {
			throw new BadInputException("Invalid input for entity change: Dto must be not null and dto.id must be not null.");
		}
		if (findById(dto.getId()) == null) {
			throw new DataNotFoundException(String.format("Can't make changes: no entity with id %s found.", dto.getId()));
		}
		dto.setName(nameToStandardForSave(dto.getName()));
		return mapToDto(save(mapToEntity(dto)));
	}

	public boolean deleteEntity(Integer id) {
		if (id == null) {
			throw new BadInputException("Invalid input for entity delete operation: id must be not null.");
		}
		if (findById(id) == null) {
			throw new DataNotFoundException(String.format("Can't delete entity: no entity with id %s found.", id));
		}
		return deleteById(id);
	}

	public List<ListItemDto> findBySearchParams(SearchParams params) {
		if (!StringUtils.isBlank(params.getNamePart())) {
			return findListDtoByNamePart(params.getNamePart());
		}
		return findAllListDto();
	};


	protected String nameToStandard(String name) {
		if (StringUtils.isBlank(name)) {
			throw new BadInputException("Invalid input: name must be not blank.");
		}
		return name.toUpperCase()+"%";
	}

	protected String nameToStandardForSave(String name) {
		if (StringUtils.isBlank(name)) {
			throw new BadInputException("Invalid input: name must be not blank.");
		}
		return name.toUpperCase();
	}

	protected void checkInputId(Integer id) {
		if (id == null) {
			throw new BadInputException("Invalid input for operation: id must be not null.");
		}
		if (findById(id) == null) {
			throw new DataNotFoundException(String.format("Can't execute operation: no entity with id %s found.", id));
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
