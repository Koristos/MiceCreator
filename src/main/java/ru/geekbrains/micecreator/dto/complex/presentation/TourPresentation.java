package ru.geekbrains.micecreator.dto.complex.presentation;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TourPresentation {
	private String country;
	private String startDate;
	private String endDate;
	private List<AirlineDesc> airlines = new ArrayList<>();
	private List<AccLocation> accLocations = new ArrayList<>();
	private List<EventPlace> eventHotels = new ArrayList<>();
	private List<EventPlace> eventRegions = new ArrayList<>();
}
