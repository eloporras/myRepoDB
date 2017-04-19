package edu.gatech.cs6400.team81.dao;

public interface ClientSQL {
	public static final String GET_BY_NAME = "SELECT * FROM Client c WHERE NAME LIKE ?";
	public static final String GET_BY_DESCRIPTION = "SELECT * FROM Client c WHERE DESCRIPTION LIKE ?";
	public static final String GET_BY_IDENTIFIER = "SELECT * FROM Client c WHERE IDENTIFIER = ?";
	public static final String ADD = "INSERT INTO Client (DESCRIPTION, NAME, PHONE) VALUES(?, ?, ?)";
	
	public static final String SEARCH = "SELECT * FROM Client c";
	
	public static final String UPDATE_BY_CLIENTID = "UPDATE Client SET DESCRIPTION = ?, NAME = ?, PHONE = ? WHERE Identifier = ?";

	public static final String GET_UNASSIGNED_FOR_SITEID = "SELECT * FROM Client WHERE Identifier NOT IN (SELECT ClientId From WaitList WHERE SiteId = ?)";
}
