package ru.geekbrains.micecreator.dto.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchParams {

	private String namePart;
	private Integer countryId;
	private Integer regionId;
	private Integer locationId;
	private Integer hotelId;
	private boolean isUsingAlterNames;

}
