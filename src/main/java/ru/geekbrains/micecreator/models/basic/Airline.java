package ru.geekbrains.micecreator.models.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "airlines")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Airline {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_airlines")
	private Integer id;

	@Column(name = "airline_name")
	private String name;

	@Column(name = "airline_desc")
	private String description;

}
