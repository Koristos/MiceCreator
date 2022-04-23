package ru.geekbrains.micecreator.models.basic;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "hotels")
@NoArgsConstructor
@Data
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_hotels")
	private Integer id;

	@Column(name = "hotel_name")
	private String name;

	@Column(name = "hotel_desc")
	private String description;

	@ManyToOne
	@JoinColumn(name = "location_id")
	private Location location;

	@OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
	List<HotelService> services;

	@OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
	List<Room> rooms;

}

