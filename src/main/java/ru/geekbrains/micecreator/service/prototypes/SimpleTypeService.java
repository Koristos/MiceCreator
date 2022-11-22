package ru.geekbrains.micecreator.service.prototypes;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.geekbrains.micecreator.dto.basic.SearchParams;
import ru.geekbrains.micecreator.dto.basic.list.ListItemDto;
import ru.geekbrains.micecreator.dto.basic.prototype.BasicDto;
import ru.geekbrains.micecreator.exceptions.BadInputException;
import ru.geekbrains.micecreator.exceptions.DataNotFoundException;
import ru.geekbrains.micecreator.utils.AppUtils;

import java.util.List;
import java.util.stream.Collectors;

public abstract class SimpleTypeService<D extends BasicDto, E> {

	private static final Logger logger = LogManager.getLogger(SimpleTypeService.class);

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
			String message = "Invalid input for entity creation: Dto must be not null and have no id.";
			logger.error(message);
			throw new BadInputException(message);
		}
		dto.setName(nameToStandardForSave(dto.getName()));
		return mapToDto(save(mapToEntity(dto)));
	}

	public D editEntity(D dto) {
		if (dto == null || dto.getId() == null) {
			String message = "Invalid input for entity change: Dto must be not null and dto.id must be not null.";
			logger.error(message);
			throw new BadInputException(message);
		}
		if (findById(dto.getId()) == null) {
			String message = String.format("Can't make changes: no entity with id %s found.", dto.getId());
			logger.error(message);
			throw new DataNotFoundException(message);
		}
		dto.setName(nameToStandardForSave(dto.getName()));
		return mapToDto(save(mapToEntity(dto)));
	}

	public boolean deleteEntity(Integer id) {
		if (id == null) {
			String message = "Invalid input for entity delete operation: id must be not null.";
			logger.error(message);
			throw new BadInputException(message);
		}
		if (findById(id) == null) {
			String message = String.format("Can't delete entity: no entity with id %s found.", id);
			logger.error(message);
			throw new DataNotFoundException(message);
		}
		return deleteById(id);
	}

	public List<ListItemDto> findBySearchParams(SearchParams params) {
		if (!AppUtils.isBlank(params.getNamePart())) {
			return findListDtoByNamePart(params.getNamePart());
		}
		return findAllListDto();
	}


	protected String nameToStandard(String name) {
		if (AppUtils.isBlank(name)) {
			String message = "Invalid input: name must be not blank.";
			logger.error(message);
			throw new BadInputException(message);
		}
		return name.toUpperCase()+"%";
	}

	protected String nameToStandardForSave(String name) {
		if (AppUtils.isBlank(name)) {
			String message = "Invalid input: name must be not blank.";
			logger.error(message);
			throw new BadInputException(message);
		}
		return name.toUpperCase();
	}

	protected void checkInputId(Integer id) {
		if (id == null) {
			String message = "Invalid input for operation: id must be not null.";
			logger.error(message);
			throw new BadInputException(message);
		}
		if (findById(id) == null) {
			String message = String.format("Can't execute operation: no entity with id %s found.", id);
			logger.error(message);
			throw new DataNotFoundException(message);
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
