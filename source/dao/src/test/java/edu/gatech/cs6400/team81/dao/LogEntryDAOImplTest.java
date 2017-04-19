package edu.gatech.cs6400.team81.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;

import edu.gatech.cs6400.team81.model.LogEntry;
import edu.gatech.cs6400.team81.model.Site;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-beans-test.xml" })
public class LogEntryDAOImplTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Autowired
	private LogEntryDAO underTest;

	@Autowired
	private SimpleJdbcTemplate jdbcTemplate;

	@Test
	public void testAdd() throws Exception {
		Site site = new Site();
		site.setId(2);
		
		LogEntry logEntry = new LogEntry(1, 1, "JUnit DescOfService");
		logEntry.setClientId(1);
		logEntry.setNotes("JUnit Notes");
		
		int rowsBefore = SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "LogEntry");
		boolean actual = underTest.add(logEntry);
		int rowsAfter = SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "LogEntry");
		assertNotNull(actual);
		assertEquals(rowsBefore + 1, rowsAfter);
		
	}
	
	@Test
	public void testGetByClientId() throws Exception{
		List<LogEntry> actual = underTest.getByClientId(2);
		assertEquals("The number of records returned is wrong", 3, actual.size());		
	}	

}
