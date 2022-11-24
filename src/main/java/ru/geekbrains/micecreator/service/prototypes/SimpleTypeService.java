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

	/**
	 * Метод для получения всех сущностей определенного типа в виде DTO
	 * @return список DTO всех сущностей
	 */
	public List<ListItemDto> findAllListDto() {
		return findAll().stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	/**
	 * Метод возвращает список сущностей в виде DTO по соответствию части имени
	 * @param namePart часть имени сущности
	 * @return список DTO найденных сущностей
	 */
	public List<ListItemDto> findListDtoByNamePart(String namePart) {
		namePart = nameToStandard(namePart);
		return findByNamePart(namePart).stream().map(this::mapToListItemDto).collect(Collectors.toList());
	}

	/**
	 * Метод возвращает сущность в виде спискового DTO по id
	 * @param id id сущности
	 * @return сущность в виде спискового DTO
	 */
	public ListItemDto findListDtoById(Integer id) {
		checkInputId(id);
		E type = findById(id);

		if (type == null) {
			return null;
		}
		return mapToListItemDto(type);
	}

	/**
	 * Метод возвращает сущность в виде DTO по id
	 * @param id id сущности
	 * @return сущность в виде DTO
	 */
	public D findDtoById(Integer id) {
		checkInputId(id);
		E type = findById(id);

		if (type == null) {
			return null;
		}
		return mapToDto(type);
	}

	/**
	 * Метод создает новую сущность определенного типа
	 * ID в этом случае не должно передаваться
	 * @param dto DTO создаваемой сущности
	 * @return DTO созданной сущности
	 */
	public D createEntity(D dto) {
		if (dto == null || dto.getId() != null) {
			String message = "Invalid input for entity creation: Dto must be not null and have no id.";
			logger.error(message);
			throw new BadInputException(message);
		}
		dto.setName(nameToStandardForSave(dto.getName()));
		return mapToDto(save(mapToEntity(dto)));
	}

	/**
	 * Метод редактирует уже имеющуюся сущность
	 * ID обязателен
	 * @param dto DTO редактируемой сущности
	 * @return DTO отредактированной сущности
	 */
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

	/**
	 * Метод удаляет сущность по ID
	 * @param id id сущности
	 * @return true, если все прошло хорошо
	 */
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

	/**
	 * Метод ищет все сущности по заданным параметрам. Параметры можно использовать в реализации в зависимости от потребностей
	 * конкретной сущности
	 * @param params параметры для поиска сущностей
	 * @return список найденных сущностей в виде DTO
	 */
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
