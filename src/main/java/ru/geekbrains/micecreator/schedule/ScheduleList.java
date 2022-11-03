package ru.geekbrains.micecreator.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.geekbrains.micecreator.service.currency.CbCurrencyService;

@Service
@EnableScheduling
public class ScheduleList {

	@Autowired
	CbCurrencyService cbCurrencyService;


	@Scheduled(cron="0 0 8 * * *")
	public void updateCBCourses() {
		cbCurrencyService.updateCurrencyInfo();
	}

}
