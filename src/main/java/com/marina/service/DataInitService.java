package com.marina.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.marina.model.Area;
import com.marina.model.Corona;
import com.marina.parser.ListHolder;
import com.marina.parser.Parser;
import com.marina.repo.AreaRepo;
import com.marina.repo.CoronaRepo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DataInitService {

	@Autowired
	private Parser parser;

	@Autowired
	private AreaRepo areaRepo;

	@Autowired
	private CoronaRepo coronaRepo;

	public String initData() {
		ListHolder lists = parser.parseJson();
		return emptyAndSaveDataToDb(lists);
	}

	@Transactional
	public String emptyAndSaveDataToDb(ListHolder lists) {

		if (lists == null || lists.getAreas() == null || lists.getAreas().isEmpty() || lists.getCases() == null
				|| lists.getCases().isEmpty())
			return "Data missing";

		emptyTables();

		List<Area> areas = lists.getAreas();
		List<Area> savedAreas = areaRepo.saveAll(areas);
		int areasCount = (savedAreas == null) ? 0 : savedAreas.size();

		List<Corona> lines = lists.getCases();
		List<Corona> savedLines = coronaRepo.saveAll(lines);
		int linesCount = (savedLines == null) ? 0 : savedLines.size();

		return String.format("Saved %s areas,  %s lines.", areasCount, linesCount);
	}

	private void emptyTables() {
		areaRepo.deleteAll();
		coronaRepo.deleteAll();

	}

}
