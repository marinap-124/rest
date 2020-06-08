package com.marina.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableMap;
import com.marina.model.Area;
import com.marina.model.Corona;
import lombok.extern.slf4j.Slf4j;
import no.ssb.jsonstat.JsonStatModule;
import no.ssb.jsonstat.v2.Dataset;
import no.ssb.jsonstat.v2.DatasetBuildable;
import no.ssb.jsonstat.v2.Dimension;
import no.ssb.jsonstat.v2.Dimension.Category;

@Slf4j
@Component

public class Parser {

	@Value("${parser.json.url}")
	private String url;

	@Value("${parser.json.dimension.area}")
	private String areaDimensionName;

	@Value("${parser.json.dimension.week}")
	private String weekDimensionName;

	public static final String STAT_VERSION = "\"version\": \"2.0\",";

	public ListHolder parseJson() {

		ListHolder holder = new ListHolder();
		try {
			String json = getJson();
			holder = convertJsonToObject(json);

		} catch (Exception ex) {
			log.error("Error parsing json: " + ex.getMessage());
		}

		return holder;

	}

	ListHolder convertJsonToObject(String json) {

		ListHolder holder = new ListHolder();

		if (json == null || json.isEmpty())
			return null;

		Dataset build = getDataset(json);
		if (build == null)
			return null;

		Map<String, Dimension> dimensions = build.getDimension();
		if (dimensions == null)
			return null;

		Dimension dimension = dimensions.get(areaDimensionName);
		List<Area> areas = getAreaDimension(dimension);

		holder.setAreas(areas);

		dimension = dimensions.get(weekDimensionName);
		Map<String, String> weeks = getDateweekDimension(dimension);

		List<Corona> cases = getCoronaCases(build, weeks);
		holder.setCases(cases);

		log.debug("CASES size:" + cases.size());

		return holder;

	}

	private Map<String, String> getDateweekDimension(Dimension dimension) {

		Category category = dimension.getCategory();
		ImmutableMap<String, String> labelMap = category.getLabel();

		if (labelMap == null)
			return null;

		Map<String, String> map = new HashMap<>();

		for (Map.Entry<String, String> ent : labelMap.entrySet()) {
			map.put(ent.getKey(), ent.getValue());
		}

		return map;
	}

	private List<Area> getAreaDimension(Dimension dimension) {

		Category category = dimension.getCategory();
		ImmutableMap<String, String> labelMap = category.getLabel();

		if (labelMap == null)
			return null;

		List<Area> list = new ArrayList<Area>();
		Area area = null;
		for (Map.Entry<String, String> ent : labelMap.entrySet()) {
			area = new Area();
			area.setCode(ent.getKey());
			area.setLabel(ent.getValue());
			list.add(area);
		}

		return list;
	}

	private List<Corona> getCoronaCases(Dataset build, Map<String, String> weeks) {

		List<Corona> list = new LinkedList<>();
		Corona corona = null;
		Map<List<String>, Number> listListMap = build.asMap();
		for (Map.Entry<List<String>, Number> listListEntry : listListMap.entrySet()) {
			corona = new Corona();
			List<String> key = listListEntry.getKey();
			corona.setAreaCode(key.get(0));

			int count = (listListEntry.getValue() == null) ? 0 : listListEntry.getValue().intValue();
			corona.setCount(count);
			String week = (weeks.get(key.get(1)) == null) ? "" : weeks.get(key.get(1)).replace("Vuosi", "").trim();
			corona.setWeekName(week);
			list.add(corona);
		}

		return list;
	}

	private Dataset getDataset(String json) {

		try {
			ObjectMapper mapper = getObjectMapper();
			JsonParser parser = mapper.getFactory().createParser(json);
			TypeReference<Map<String, DatasetBuildable>> ref = new TypeReference<Map<String, DatasetBuildable>>() {
			};
			Map<String, DatasetBuildable> map = mapper.readValue(parser, ref);
			DatasetBuildable next = map.values().iterator().next();

			return next.build();
		} catch (Exception ex) {
			log.error("Error getDataset:" + ex.getMessage());
			return null;
		}
	}

	private ObjectMapper getObjectMapper() {

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new GuavaModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
		mapper.registerModule(new JsonStatModule());

		return mapper;
	}

	private String readJson(String url) {

		try (InputStream inputStream = new URL(url).openStream()) {
			return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}

	String modifyJson(String json) {
		return json.replace(STAT_VERSION, "");
	}

	private String getJson() {
		String json = readJson(url);
		json = modifyJson(json);
		return json;
	}

}
