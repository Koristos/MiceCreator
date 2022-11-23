package ru.geekbrains.micecreator.models.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.models.currency.Currency;

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
@Table(name = "countries")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_countries")
	private Integer id;

	@Column(name = "country_name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "currency_id")
	private Currency currency;

	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private List<Region> regions;

}
