package ru.geekbrains.micecreator.dto.complex;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.models.complex.Accommodation;

import java.util.List;

@Data
@NoArgsConstructor
public class TourDtoFull {

	private TourDto tour;
	private List<Accommodation> accommodations;
	private List<FlightDto> flights;
	private List<HotelEventDto> hotelEvents;
	private List<RegionEventDto> regionEvents;

}
