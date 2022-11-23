package ru.geekbrains.micecreator.models.basic;

import lombok.AllArgsConstructor;
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
@Table(name = "airports")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Airport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_airports")
	private Integer id;

	@Column(name = "airport_code")
	private String code;

	@Column(name = "airport_name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "region_id")
	private Region region;
}