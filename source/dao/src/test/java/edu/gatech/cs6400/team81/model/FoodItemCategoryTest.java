package edu.gatech.cs6400.team81.model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FoodItemCategoryTest {
	@Test
	public void testByNameVegetables(){
		FoodItemCategory actual = FoodItemCategory.byName("Vegetables");
		assertEquals(FoodItemCategory.VEGETABLES, actual);
	}
	@Test
	public void testByNameNutsGrainsBeans(){
		FoodItemCategory actual = FoodItemCategory.byName("Nuts/Grains/Beans");
		assertEquals(FoodItemCategory.NUTS_GRAINS_BEANS, actual);
	}

	@Test
	public void testByNameMeatSeafood(){
		FoodItemCategory actual = FoodItemCategory.byName("Meat/Seafood");
		assertEquals(FoodItemCategory.MEAT_SEAFOOD, actual);
	}

	@Test
	public void testByNameDairyEggs(){
		FoodItemCategory actual = FoodItemCategory.byName("Dairy/Eggs");
		assertEquals(FoodItemCategory.DAIRY_EGGS, actual);
	}

	@Test
	public void testByNameSauceCondimentSeasoning(){
		FoodItemCategory actual = FoodItemCategory.byName("Sauce/Condiment/Seasoning");
		assertEquals(FoodItemCategory.SAUCE_CONDIMENT_SEASONING, actual);
	}

	@Test
	public void testByName(){
		FoodItemCategory actual = FoodItemCategory.byName("Juice/Drink");
		assertEquals(FoodItemCategory.JUICE_DRINK, actual);
	}

}
