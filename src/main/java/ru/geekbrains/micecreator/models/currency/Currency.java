package ru.geekbrains.micecreator.models.currency;

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
@Table(name = "currency")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Currency {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_currency")
	private Integer id;

	@Column(name = "currency_name")
	private String name;

}
