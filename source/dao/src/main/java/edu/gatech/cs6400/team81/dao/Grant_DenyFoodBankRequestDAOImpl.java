package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import edu.gatech.cs6400.team81.model.Item;

@Repository
public class Grant_DenyFoodBankRequestDAOImpl extends BaseDAO<Item> implements Grant_DenyFoodBankRequestDAO, Grant_DenyFoodBankRequestSQL {	
	
	@Override
	public void grantRequest(int numFilled, int requestId) throws SQLException{		
		execute(GRANT_REQUEST, new Object[]{numFilled, requestId});
		
	}
	
	@Override
	public void reduceItemCount(int numToReduce, int itemId) throws SQLException{
		execute(REDUCE_ITEMS, new Object[]{numToReduce, itemId});
	}

	@Override
	public void denyRequest(int requestId) throws SQLException{
		execute(DENY_REQUEST, new Object[]{requestId});
		
	}

	@Override
	protected Item mapResult(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}



}
