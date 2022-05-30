package ru.geekbrains.micecreator.dto.complex.presentation;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.full.HotelDto;
import ru.geekbrains.micecreator.dto.basic.full.RegionDto;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class EventPlace {
	private String name;
	private List<EventDesc> events = new ArrayList<>();

	public EventPlace(HotelDto place) {
		this.name = place.getName();
	}

	public EventPlace(RegionDto place) {
		this.name = place.getName();
	}

	@Override
	public int hashCode() {
		return "EventPlace".hashCode() + this.name.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		return this.hashCode() == o.hashCode();
	}
}

