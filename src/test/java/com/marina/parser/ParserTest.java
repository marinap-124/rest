package com.marina.parser;

import org.junit.Assert.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ParserTest {

	@Autowired
	Parser parser;

	private static final String path = "com/marina/parser/test.json";

	@Test
	public void testJsonParsing() {
		String json = loadJson(path);
		json = parser.modifyJson(json);
		assertFalse(json.contains(Parser.STAT_VERSION));

		ListHolder holder = parser.convertJsonToObject(json);
		assertNotNull(holder);
		assertNotNull(holder.areas);
		assertNotNull(holder.cases);
		assertTrue(holder.areas.size() == 22);
		assertTrue(holder.cases.size() == 1188);
	}

	private String loadJson(String path) {

		ClassLoader classLoader = getClass().getClassLoader();
		try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
			String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
			return result;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
