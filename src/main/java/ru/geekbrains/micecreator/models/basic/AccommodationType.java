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
@Table(name = "acc_types")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccommodationType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_acc_types")
	private Integer id;

	@Column(name = "acc_type_name")
	private String name;

	@Column(name = "acc_number")
	private Integer paxNumber;
}
