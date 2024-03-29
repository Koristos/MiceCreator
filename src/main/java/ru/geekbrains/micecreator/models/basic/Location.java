package ru.geekbrains.micecreator.models.basic;

import lombok.AllArgsConstructor;
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
@Table(name = "locations")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_locations")
	private Integer id;

	@Column(name = "location_name")
	private String name;

	@Column(name = "location_desc")
	private String description;

	@Column(name = "image_one")
	private String imageOne;

	@ManyToOne
	@JoinColumn(name = "region_id")
	private Region region;

	@OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
	private List<Hotel> hotels;

}