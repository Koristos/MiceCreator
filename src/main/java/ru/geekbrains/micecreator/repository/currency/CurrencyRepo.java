package ru.geekbrains.micecreator.repository.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.micecreator.models.currency.Currency;

import java.util.List;

public interface CurrencyRepo extends JpaRepository<Currency, Integer> {

	List<Currency> findByName (String name);

}
