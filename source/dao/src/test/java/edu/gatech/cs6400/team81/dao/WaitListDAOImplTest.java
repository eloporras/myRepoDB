package edu.gatech.cs6400.team81.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.gatech.cs6400.team81.model.WaitList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-beans-test.xml" })
public class WaitListDAOImplTest {

	private static final int SITEID = 1;
	
	@Autowired
	private WaitListDAO underTest;
	
	@Test
	public void testGetBySiteId() throws Exception {
		List<WaitList> actual = underTest.getBySiteId(SITEID);
		assertNotNull(actual);
		assertEquals(2, actual.size());
	}

}
