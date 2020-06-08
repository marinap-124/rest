package com.marina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.marina.parser.ListHolder;
import com.marina.parser.Parser;
import com.marina.repo.CoronaRepo;

import lombok.extern.slf4j.Slf4j;

@EnableScheduling
@Slf4j
@Service
public class UpdatingProcessor {

	@Autowired
	private DataInitService dataInitService;

	@Autowired
	private Parser parser;

	@Autowired
	private CoronaRepo coronaRepo;

	@Scheduled(cron = "${processor.update.data.cron}")
	public void updateData() {
		try {
			log.debug("Start scheduled task");
			ListHolder holder = parser.parseJson();

			if (holder == null || holder.getAreas() == null || holder.getCases() == null
					|| holder.getAreas().size() == 0 || holder.getCases().size() == 0)
				return;

			if (coronaRepo.count() >= holder.getCases().size())
				return;

			String message = dataInitService.emptyAndSaveDataToDb(holder);
			log.info(message);
			log.debug("End scheduled task");

		} catch (Exception ex) {
			log.error("Error updating data " + ex.getMessage());
		}
	}
}
