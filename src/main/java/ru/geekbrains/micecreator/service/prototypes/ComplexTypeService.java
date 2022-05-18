package ru.geekbrains.micecreator.service.prototypes;

import ru.geekbrains.micecreator.dto.complex.prototype.IdPositive;
import ru.geekbrains.micecreator.exceptions.BadInputException;
import ru.geekbrains.micecreator.exceptions.DataNotFoundException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class ComplexTypeService<D extends IdPositive, E> {

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
			throw new BadInputException("Invalid input for entity creation: Dto must be not null and have no id.");
		}
		return mapToDto(save(mapToEntity(dto)));
	}

	public D editEntity(D dto) {
		if (dto == null || dto.getId() == null) {
			throw new BadInputException("Invalid input for entity change: Dto must be not null and dto.id must be not null.");
		}
		if (findById(dto.getId()) == null) {
			throw new DataNotFoundException(String.format("Can't make changes: no entity with id %s found.", dto.getId()));
		}
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

	protected void checkInput(Object... input) {
		if (Arrays.stream(input).anyMatch(Objects::isNull)) {
			throw new BadInputException("Invalid input operation: one of necessary params is null.");
		}
	}

	protected void checkDates(LocalDate firstDate, LocalDate secondDate) {
		if (firstDate.compareTo(secondDate) > 0) {
			throw new BadInputException(String.format("Wrong date range: firstDate %s must be earlier than secondDate %s.", firstDate, secondDate));
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
