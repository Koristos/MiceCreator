package ru.geekbrains.micecreator.models.currency;

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
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "course")
@NoArgsConstructor
@Data
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_course")
	private Integer id;

	@Column(name = "course_date")
	private LocalDate courseDate;

	@Column(name = "course_rate")
	private BigDecimal rate;

	@ManyToOne
	@JoinColumn(name = "currency_id")
	private Currency currency;
}
