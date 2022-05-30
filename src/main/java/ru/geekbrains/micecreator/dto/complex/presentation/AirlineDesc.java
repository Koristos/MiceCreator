package ru.geekbrains.micecreator.dto.complex.presentation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AirlineDesc {
	private String name;
	private String desc;

	@Override
	public int hashCode() {
		return "AirlineDesc".hashCode() + this.name.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		return this.hashCode() == o.hashCode();
	}
}
