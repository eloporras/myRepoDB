package edu.gatech.cs6400.team81.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-beans-test.xml" })
public class FoodItemDAOImplTest {

	@Autowired
	private FoodItemDAO underTest;
	
	@Test
	public void testGetMealCounts() throws Exception {
		List<Map<String, Object>> result = underTest.getAllMealCounts();
		assertNotNull(result);
		assertEquals(2, result.size());
	}

}
