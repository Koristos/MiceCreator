package ru.geekbrains.micecreator.dto.complex.presentation;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.full.LocationDto;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AccLocation {
	private String name;
	private String desc;
	private Path imageLink;
	private List<AccHotel> accHotels = new ArrayList<>();

	public AccLocation(LocationDto location) {
		this.name = location.getName();
		this.desc = location.getDescription();
	}

	@Override
	public int hashCode() {
		return "AccLocation".hashCode() + this.name.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		return this.hashCode() == o.hashCode();
	}
}
