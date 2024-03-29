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
@Table(name = "regions")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Region {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_regions")
	private Integer id;

	@Column(name = "region_name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;

	@Column(name = "image_one")
	private String imageOne;

	@OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
	private List<Airport> airports;

	@OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
	private List<Location> locations;

	@OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
	private List<RegionServ> services;

}
