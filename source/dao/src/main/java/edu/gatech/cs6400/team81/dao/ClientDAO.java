package edu.gatech.cs6400.team81.dao;

import java.sql.SQLException;
import java.util.List;

import edu.gatech.cs6400.team81.model.Client;

public interface ClientDAO {
	public static final String TABLE = "Client";

	public static final String IDENTIFIER = "Identifier";
	public static final String DESCRIPTION = "Description";
	public static final String NAME = "Name";
	public static final String PHONE = "Phone";

	List<Client> getUnassignedForSite(int siteId) throws SQLException;
	
	Client getByIdentifier(int id) throws SQLException;

	List<Client> getByClientName(String clientname) throws SQLException;
	
	List<Client> getByDescription(String desc) throws SQLException;

	Integer createClient(Client client) throws SQLException;

	Integer createClient(String desc, String name, String phone) throws SQLException;
	
	String update(Client updates) throws SQLException;
	
	List<Client> search(String whereClause) throws SQLException;

}