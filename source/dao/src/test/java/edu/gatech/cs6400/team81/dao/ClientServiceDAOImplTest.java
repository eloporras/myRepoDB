package edu.gatech.cs6400.team81.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
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

import edu.gatech.cs6400.team81.model.ClientService;
import edu.gatech.cs6400.team81.model.ServiceCategory;
import edu.gatech.cs6400.team81.model.Site;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-beans-test.xml" })
public class ClientServiceDAOImplTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Autowired
	private ClientServiceDAO underTest;
	
	@Autowired
	private SimpleJdbcTemplate jdbcTemplate;
	
	private static final int FOOD_PANTRY_SERVICE_ID = 1;
	private static final int SHELTER_SERVICE_ID = 2;
	private static final int SOUP_KITCHEN_SERVICE_ID = 3;
	private static final int SITE1_ID = 1;
	private static final int SITE2_ID = 2;
	private static final Site SITE1 = new Site();
	private static final Site SITE2 = new Site();

	static{
		SITE1.setId(SITE1_ID);		
		SITE2.setId(SITE2_ID);
	}
	
	@Test
	public void testGetBySiteId() throws SQLException {
		List<ClientService> actual = underTest.getBySiteId(SITE1_ID);
		assertNotNull(actual);
		assertEquals(3, actual.size());
	}

	@Test
	public void testDelete() throws SQLException {
		int rowsBefore = SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "ClientService");
		boolean actual = underTest.delete(FOOD_PANTRY_SERVICE_ID);
		int rowsAfter = SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "ClientService");

		assertTrue(actual);
		assertEquals(rowsBefore - 1, rowsAfter);
	}

	@Test
	public void testGetByServiceId() throws SQLException {
		ClientService actual = underTest.getByServiceId(SOUP_KITCHEN_SERVICE_ID);
		assertNotNull(actual);
		assertEquals(SOUP_KITCHEN_SERVICE_ID, actual.getServiceId());
		assertNotNull(actual.getSite());
		assertEquals(SITE1_ID, actual.getSite().getId());
	}

	@Test
	public void testGetBySiteIdCategory() throws SQLException {
		ClientService actual = underTest.getBySiteIdCategory(SITE1_ID, ServiceCategory.SHELTER);
		assertNotNull(actual);
		assertEquals(SHELTER_SERVICE_ID, actual.getServiceId());
		assertNotNull(actual.getSite());
		assertEquals(SITE1_ID, actual.getSite().getId());
	}

	@Test
	public void testAdd_SoupKitchen_UniqueKeyViolation() throws SQLException {
		expectedException.expect(SQLException.class);
		
		ClientService newService = new ClientService();
		newService.setConditionUse("JUNIT input for conditionUse");
		newService.setDescription("JUNIT input for description");
		newService.setHoursOperation("JUNIT input for hoursOperation");
		newService.setServiceCategory(ServiceCategory.SOUPKITCHEN);
		newService.setSite(SITE1);
		
		underTest.add(newService);
	}
	
	@Test
	public void testAdd_SoupKitchen() throws SQLException {
		ClientService newService = new ClientService();
		newService.setConditionUse("JUNIT input for conditionUse");
		newService.setDescription("JUNIT input for description");
		newService.setHoursOperation("JUNIT input for hoursOperation");
		newService.setServiceCategory(ServiceCategory.SOUPKITCHEN);
		newService.setSite(SITE2);
		
		int rowsBefore = SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "ClientService");
		ClientService actual = underTest.add(newService);
		int rowsAfter = SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "ClientService");

		assertNotNull(actual);
		assertEquals(rowsBefore + 1, rowsAfter);
	}
	
	@Test
	public void testAdd_Shelter() throws SQLException {
		ClientService newService = new ClientService();
		newService.setConditionUse("JUNIT input for conditionUse");
		newService.setDescription("JUNIT input for description");
		newService.setHoursOperation("JUNIT input for hoursOperation");
		newService.setServiceCategory(ServiceCategory.SHELTER);
		newService.setSite(SITE2);
		
		int rowsBefore = SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "ClientService");
		ClientService actual = underTest.add(newService);
		int rowsAfter = SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "ClientService");

		assertNotNull(actual);
		assertEquals(rowsBefore + 1, rowsAfter);
	}

	@Test
	public void testAdd_FoodPantry() throws SQLException {
		ClientService newService = new ClientService();
		newService.setConditionUse("JUNIT input for conditionUse");
		newService.setDescription("JUNIT input for description");
		newService.setHoursOperation("JUNIT input for hoursOperation");
		newService.setServiceCategory(ServiceCategory.FOODPANTRY);
		newService.setSite(SITE2);
		
		int rowsBefore = SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "ClientService");
		ClientService actual = underTest.add(newService);
		int rowsAfter = SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "ClientService");

		assertNotNull(actual);
		assertEquals(rowsBefore + 1, rowsAfter);
	}
}
