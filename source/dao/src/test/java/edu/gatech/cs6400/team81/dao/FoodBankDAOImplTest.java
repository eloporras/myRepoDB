package edu.gatech.cs6400.team81.dao;
import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;

import edu.gatech.cs6400.team81.model.FoodBank;
import edu.gatech.cs6400.team81.model.ServiceCategory;
import edu.gatech.cs6400.team81.model.Site;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-beans-test.xml" })
public class FoodBankDAOImplTest {
	private static final int SITEID1 = 1;
	private static final int SITEID2 = 2;
	private static final int SITEID3 = 3;

	private static final Site SITE2 = new Site();
	private static final Site SITE3 = new Site();
	static{
		SITE2.setId(SITEID2);
		SITE3.setId(SITEID3);
	}
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Autowired
	private SimpleJdbcTemplate jdbcTemplate;
	
	@Autowired
	private FoodBankDAO underTest;
	
	@Test
	public void testGetBySiteId() throws Exception {
		FoodBank actual = underTest.getBySiteId(SITEID1);
		assertNotNull(actual);
		assertNotNull(actual.getSite());
		assertEquals(SITEID1, actual.getSite().getId());
	}

	@Test
	public void testDelete() throws Exception {
		int rowsBefore = SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "FoodBank");
		boolean actual = underTest.delete(SITEID1);
		int rowsAfter = SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "FoodBank");

		assertTrue(actual);
		assertEquals(rowsBefore - 1, rowsAfter);
	}

	@Test
	public void testAdd() throws Exception {
		FoodBank foodBank = new FoodBank();
		foodBank.setSite(SITE3);
		foodBank.setServiceCategory(ServiceCategory.FOODBANK);
		int rowsBefore = SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "FoodBank");
		boolean actual = underTest.add(foodBank);
		int rowsAfter = SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "FoodBank");
		assertTrue(actual);
		assertEquals(rowsBefore + 1, rowsAfter);

	}

}
