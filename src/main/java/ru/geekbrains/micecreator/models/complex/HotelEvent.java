package ru.geekbrains.micecreator.models.complex;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.models.basic.HotelServ;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "hotel_events")
@NoArgsConstructor
@Data
public class HotelEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_hotel_events")
	private Integer id;

	@Column(name = "hotel_events_date")
	private LocalDate date;

	@Column(name = "pax")
	private Integer pax;

	@Column(name = "price")
	private BigDecimal price;

	@ManyToOne
	@JoinColumn(name = "tour_id")
	private Tour tour;

	@ManyToOne
	@JoinColumn(name = "hotel_service_id")
	private HotelServ service;

}

