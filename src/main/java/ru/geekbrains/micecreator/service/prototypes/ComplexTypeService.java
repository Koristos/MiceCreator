package ru.geekbrains.micecreator.service.prototypes;

import ru.geekbrains.micecreator.dto.complex.prototype.IdPositive;

import java.math.BigDecimal;
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
			throw new RuntimeException("invalid input!");
		}
		return mapToDto(save(mapToEntity(dto)));
	}

	public D editEntity(D dto) {
		if (dto == null || dto.getId() == null) {
			throw new RuntimeException("invalid input!");
		}
		return mapToDto(save(mapToEntity(dto)));
	}

	public boolean deleteEntity(Integer id) {
		if (id == null) {
			throw new RuntimeException("invalid input!");
		}
		return deleteById(id);
	}

	protected void checkInput(Object... input) {
		if (Arrays.stream(input).anyMatch(Objects::isNull)) {
			throw new RuntimeException("Invalid Input!");
		}
	}

	protected void checkDates(Date firstDate, Date secondDate) {
		if (firstDate.compareTo(secondDate) > 0) {
			throw new RuntimeException("Invalid Input!");
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
