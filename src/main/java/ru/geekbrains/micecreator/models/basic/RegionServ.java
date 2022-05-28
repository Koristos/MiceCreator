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
@Table(name = "region_services")
@NoArgsConstructor
@Data
public class RegionServ {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_region_services")
	private Integer id;

	@Column(name = "region_service_name")
	private String name;

	@Column(name = "region_service_desc")
	private String description;

	@Column(name = "image_one")
	private String imageOne;

	@Column(name = "image_two")
	private String imageTwo;

	@ManyToOne
	@JoinColumn(name = "region_id")
	private Region region;

}
