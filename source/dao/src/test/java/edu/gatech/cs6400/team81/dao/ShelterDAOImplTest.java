package edu.gatech.cs6400.team81.dao;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-beans-test.xml" })
public class ShelterDAOImplTest {
	
	@Autowired
	private ShelterDAO underTest; 

//	@Test
//	public void testGetBySiteId() throws Exception {
//		throw new RuntimeException("not yet implemented");
//	}
//
//	@Test
//	public void testDelete() throws Exception {
//		throw new RuntimeException("not yet implemented");
//	}
//
//	@Test
//	public void testAdd() throws Exception {
//		throw new RuntimeException("not yet implemented");
//	}
//
//	@Test
//	public void testUpdate() throws Exception {
//		throw new RuntimeException("not yet implemented");
//	}

	@Test
	public void testGetSiteBunkRooms() throws Exception {
		List<Map<String, Object>> result = underTest.getSiteBunkRooms();
		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	public void testIncrementCount() throws Exception {
		underTest.incrementCount(1, "MaleBunkCount");
	}

}
