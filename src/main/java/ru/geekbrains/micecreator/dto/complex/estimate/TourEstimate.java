package ru.geekbrains.micecreator.dto.complex.estimate;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.dto.complex.TourDto;

import java.util.List;

@Data
@NoArgsConstructor
public class TourEstimate {

	private TourDto tour;
	private List<AccommodationEstimate> accommodations;
	private List<FlightEstimate> flights;
	private List<HotelEventEstimate> hotelEvents;
	private List<RegionEventEstimate> regionEvents;
}
