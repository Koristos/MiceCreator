package ru.geekbrains.micecreator.models.complex;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.models.basic.Country;

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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tours")
@NoArgsConstructor
@Data
public class Tour {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tours")
	private Integer id;

	@Column(name = "pax")
	private Integer pax;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "total_price")
	private BigDecimal totalPrice;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;

	@OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
	List<Accommodation> accommodations;

	@OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
	List<Flight> flights;

	@OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
	List<HotelEvent> hotelEvents;

	@OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
	List<RegionEvent> regionEvents;

}

