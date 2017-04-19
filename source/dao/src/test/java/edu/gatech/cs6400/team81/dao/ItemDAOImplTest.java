package edu.gatech.cs6400.team81.dao;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.gatech.cs6400.team81.model.Item;
import edu.gatech.cs6400.team81.model.ItemCategory;
import edu.gatech.cs6400.team81.model.ItemStorageType;
import edu.gatech.cs6400.team81.model.Site;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring-beans-test.xml" })
public class ItemDAOImplTest {

	private static final int SITEID = 2;
	
	@Autowired
	private ItemDAO underTest;
	
	@Test
	public void testAdd() throws Exception {
		Site site = new Site();
		site.setId(SITEID);
		Item item = new Item();
		item.setCategory(ItemCategory.FOOD);
		item.setExpireDate(new Date());
		item.setName("name");
		item.setNumberUnits(10);
		item.setSite(site);
		item.setStorageType(ItemStorageType.DRYGOOD);
		
		Item actual = underTest.add(item);
		assertNotNull(actual);
		assertTrue(actual.getItemId() > 0);
	}

}
