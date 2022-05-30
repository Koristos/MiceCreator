package ru.geekbrains.micecreator.dto.complex.presentation;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.basic.full.HotelDto;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AccHotel {
	private String name;
	private String desc;
	private Path imageOneLink;
	private Path imageTwoLink;
	private List<AccRoom> accRooms = new ArrayList<>();

	public AccHotel(HotelDto hotel) {
		this.name = hotel.getName();
		this.desc = hotel.getDescription();
	}

	@Override
	public int hashCode() {
		return "AccHotel".hashCode() + this.name.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		return this.hashCode() == o.hashCode();
	}
}
