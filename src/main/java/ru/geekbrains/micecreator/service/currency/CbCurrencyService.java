package ru.geekbrains.micecreator.service.currency;

import generated.ValCurs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.geekbrains.micecreator.models.currency.Currency;
import ru.geekbrains.micecreator.utils.AppUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CbCurrencyService {

	private final RestTemplate restTemplate;
	@Autowired
	private CurrencyService currencyService;
	@Autowired
	private CourseService courseService;

	private static final String BASIC_CURRENCY = "RUB";
	private static final String CB_URL = "http://www.cbr.ru/scripts/XML_daily.asp";

	public CbCurrencyService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	/**
	 * Запрашивает курсы валют ЦБ и обновляет данные
	 */
	public void updateCurrencyInfo() {
		String result = this.restTemplate.getForObject(CB_URL, String.class);
		ValCurs cursList = (ValCurs) AppUtils.parseStringToObject(ValCurs.class, result);
		List<Currency> currencies = currencyService.findAllCurrencies();
		currencies.forEach(currency -> {
			if (currency.getName().equals(BASIC_CURRENCY)) {
				courseService.addCourse(currency,BigDecimal.ONE);
			} else {
				for (ValCurs.Valute valute: cursList.getValute()) {
					if(valute.getCharCode().equals(currency.getName())){
						String vRate = valute.getValue().replaceAll(",", ".");
						BigDecimal rate = new BigDecimal(vRate);
						rate = rate.setScale(2, RoundingMode.HALF_UP);
						courseService.addCourse(currency, rate);
						break;
					}
				}
			}
		});
	}
}
