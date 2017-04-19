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

import edu.gatech.cs6400.team81.model.Client;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-beans-test.xml" })
public class ClientDAOImplTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Autowired
	private ClientDAO underTest;
	
	@Autowired
	private SimpleJdbcTemplate jdbcTemplate;
	
	
	private static final Client client4 = new Client();
	
	static{
		client4.setIdentifier(4);
		client4.setDescription("Junit Test Client #4");
		client4.setName("Junit, Test4");
		client4.setPhone("555-867-5311");
	}

	@Test
	public void testGetByIdentifier() throws SQLException {
		Client actual = underTest.getByIdentifier(client4.getIdentifier());
		assertNotNull(actual);
		assertEquals(client4.getName(), actual.getName());		
	}
	
	@Test
	public void testGetByClientName() throws SQLException {
		List<Client> actual = underTest.getByClientName(client4.getName());
		assertNotNull(actual);
		assertTrue("The method 'getByClientName name returned an empty result.", !actual.isEmpty());
		assertEquals(client4.getName(), actual.get(0).getName());
	}
	
	@Test
	public void testGetByClientDescription() throws SQLException {
		List<Client> actual = underTest.getByDescription(client4.getDescription());
		assertNotNull(actual);
		assertTrue("The method 'getByClientDesc name returned an empty result.", !actual.isEmpty());
		assertEquals(client4.getDescription(), actual.get(0).getDescription());
	}

	@Test
	public void testCreateClient() throws SQLException {
		int rowsBefore = SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "Client");
		int clientId = underTest.createClient("I am a description", "John Doe", "321-555-1212");
		int rowsAfter = SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "Client");
		assertEquals("It does not appear a row has been added.  ", rowsBefore + 1, rowsAfter);
	}
	
	@Test
	public void testUpdate_SameVal() throws SQLException{
		Client client3 = new Client();
		client3.setIdentifier(3);
		client3.setDescription("Junit Test Client #3");
		client3.setName("Junit, Test3");
		client3.setPhone("555-867-5310");
		
		String logMsg = underTest.update(client3);
		assertEquals("Log message should be empty.", logMsg, "");	
	}
	
	@Test
	public void testUpdate_NewVals() throws SQLException{
		Client client2 = new Client();
		client2.setIdentifier(2);
		client2.setDescription("Updated description for client #2");
		client2.setName("Junit, Test2, Modified");
		client2.setPhone("321-555-1212");
		
		String expectedMsg = "Changed client name from 'Junit, Test2' to 'Junit, Test2, Modified'.\n" +
				"Changed description from 'Junit Test Client #2' to 'Updated description for client #2'.\n" +
				"Changed phone from '555-867-5309' to '321-555-1212'.\n";
		
		String logMsg = underTest.update(client2);
		assertEquals("Log message doesn't match expected", expectedMsg, logMsg);	
	}
	
	@Test
	public void testUpdate_Bad_Id() throws SQLException{
		String expected = "The value for a client identifier cannot be zero.";
		
		Client client0 = new Client();
		client0.setIdentifier(0);
		client0.setDescription("Updated description for client #2");
		client0.setName("Junit, Test2, Modified");
		client0.setPhone("321-555-1212");
		
		try{
			underTest.update(client0);
			assertTrue("Method did not throw IllegalArgumentException.", false);
		} catch (IllegalArgumentException iae){
			assertEquals("Messaage of thrown exception was not correct.", iae.getMessage(), expected);
		}finally{
			client4.setIdentifier(4);
		}
	}


}
