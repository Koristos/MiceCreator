package ru.geekbrains.micecreator.service.prototypes;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.geekbrains.micecreator.dto.complex.prototype.IdPositive;
import ru.geekbrains.micecreator.exceptions.BadInputException;
import ru.geekbrains.micecreator.exceptions.DataNotFoundException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class ComplexTypeService<D extends IdPositive, E> {

	private static final Logger logger = LogManager.getLogger(ComplexTypeService.class);

	public List<D> findAllDto() {
		return findAll().stream().map(this::mapToDto).collect(Collectors.toList());
	}

	public List<D> findDtoByTourId(Integer tourId) {
		checkInput(tourId);
		return findByTour(tourId).stream().map(this::mapToDto).collect(Collectors.toList());
	}

	public D findDtoById(Integer id) {
		checkInput(id);
		E entity = findById(id);

		if (entity == null) {
			return null;
		}
		return mapToDto(entity);
	}

	public D createEntity(D dto) {
		if (dto == null || dto.getId() != null) {
			String message = "Invalid input for entity creation: Dto must be not null and have no id.";
			logger.error(message);
			throw new BadInputException(message);
		}
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
		return mapToDto(save(mapToEntity(dto)));
	}

	public boolean deleteEntity(Integer id) {
		if (id == null) {
			String message ="Invalid input for entity delete operation: id must be not null.";
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

	protected void checkInput(Object... input) {
		if (Arrays.stream(input).anyMatch(Objects::isNull)) {
			String message = "Invalid input operation: one of necessary params is null.";
			logger.error(message);
			throw new BadInputException(message);
		}
	}

	protected void checkDates(LocalDate firstDate, LocalDate secondDate) {
		if (firstDate.compareTo(secondDate) > 0) {
			String message = String.format("Wrong date range: firstDate %s must be earlier than secondDate %s.", firstDate, secondDate);
			logger.error(message);
			throw new BadInputException(message);
		}
	}

	protected abstract List<E> findAll();

	protected abstract List<E> findByTour(Integer tourId);

	protected abstract E findById(Integer id);

	protected abstract E save(E entity);

	protected abstract boolean deleteById(Integer id);

	protected abstract D mapToDto(E entity);

	protected abstract E mapToEntity(D dto);

}
