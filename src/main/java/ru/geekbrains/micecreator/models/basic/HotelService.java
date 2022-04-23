package ru.geekbrains.micecreator.models.basic;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "hotel_services")
@NoArgsConstructor
@Data
public class HotelService {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_hotel_services")
	private Integer id;

	@Column(name = "hotel_service_name")
	private String name;

	@Column(name = "hotel_service_desc")
	private String description;

	@ManyToOne
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;

}