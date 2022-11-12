package ru.geekbrains.micecreator.models.complex;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.micecreator.models.basic.Country;
import ru.geekbrains.micecreator.models.security.User;

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
import java.time.LocalDate;
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
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "total_price")
	private BigDecimal totalPrice;

	@Column(name = "netto_total")
	private BigDecimal nettoTotal;

	@Column(name = "creation_date")
	private LocalDate creationDate;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;

	@OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
	private List<Accommodation> accommodations;

	@OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
	private List<Flight> flights;

	@OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
	private List<HotelEvent> hotelEvents;

	@OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
	private List<RegionEvent> regionEvents;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}

