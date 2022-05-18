package ru.geekbrains.micecreator.dto.complex;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TourDtoFull {

	private TourDto tour;
	private List<AccommodationDto> accommodations;
	private List<FlightDto> flights;
	private List<HotelEventDto> hotelEvents;
	private List<RegionEventDto> regionEvents;

}
