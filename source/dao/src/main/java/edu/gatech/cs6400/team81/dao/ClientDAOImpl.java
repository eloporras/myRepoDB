package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;
import edu.gatech.cs6400.team81.dao.BaseDAO;
import edu.gatech.cs6400.team81.dao.ClientDAO;
import edu.gatech.cs6400.team81.dao.ClientSQL;
import edu.gatech.cs6400.team81.model.Client;

@Repository
public class ClientDAOImpl extends BaseDAO<Client> implements ClientDAO, ClientSQL{

	@Override
	protected Client mapResult(ResultSet rs) throws SQLException {
		Client client = new Client();
		client.setIdentifier(rs.getInt(IDENTIFIER));
		client.setDescription(rs.getString(DESCRIPTION));
		client.setName(rs.getString(NAME));
		client.setPhone(rs.getString(PHONE));
		
		return client;
	}
	
	@Override
	public Integer createClient(Client newClient) throws SQLException {
		return executeForGeneratedKey(ADD, new Object[] {newClient.getDescription(), newClient.getName(),
				newClient.getPhone() });
		
	}
	
	public Integer createClient(String desc, String name, String phone) throws SQLException{
		Client client = new Client();
		client.setIdentifier(0);
		client.setDescription(desc);
		client.setName(name);
		client.setPhone(phone);
		return createClient(client);
	}

	@Override
	public List<Client> getByClientName(String clientname) throws SQLException {
		return getMultiple(GET_BY_NAME, new Object[] {"%" + clientname + "%"});
	}
	
	@Override
	public List<Client> getByDescription(String desc) throws SQLException {
		return getMultiple(GET_BY_DESCRIPTION, new Object[] {"%" + desc + "%"});
	}
	
	@Override
	public Client getByIdentifier(int id) throws SQLException {
		return getUnique(GET_BY_IDENTIFIER, new Object[] {id});
	}
	
	@Override
	public List<Client> search(String whereClause) throws SQLException{
		return getMultiple(SEARCH + whereClause, new Object[]{});
	}
	
	/**
	 * @param - Client object with values to be modified
	 * @return - string with the log message for the update.  If the update
	 * was not successful or if the updated values are the same as the previous 
	 * values, the string will be empty.
	 */
	@Override
	public String update(Client updates) throws SQLException {
		
		String logMsg = "";
		
		int clientId = updates.getIdentifier();
		if (clientId == 0){
			throw new IllegalArgumentException("The value for a client identifier cannot be zero.");
		}
		Client oldValues = getByIdentifier(updates.getIdentifier());
		
		int rows = execute(UPDATE_BY_CLIENTID, new Object[]{updates.getDescription(), updates.getName(), updates.getPhone(),
				updates.getIdentifier()});
		
		//Check to see if the update is successful.  If it is log the change.
		if (rows == 1){
			
			if (!oldValues.getName().equalsIgnoreCase(updates.getName())){
				logMsg += "Changed client name from '" + oldValues.getName() + "' to '" + updates.getName() + "'.\n";
			}
			
			if (!oldValues.getDescription().equalsIgnoreCase(updates.getDescription())){
				logMsg += "Changed description from '" + oldValues.getDescription() + "' to '" + updates.getDescription() + "'.\n";
			}
			
			if (!oldValues.getPhone().equalsIgnoreCase(updates.getPhone())){
				logMsg += "Changed phone from '" + oldValues.getPhone() + "' to '" + updates.getPhone() + "'.\n";
			}
		
		}		
		return logMsg;
	}

	@Override
	public List<Client> getUnassignedForSite(int siteId) throws SQLException {
		return getMultiple(GET_UNASSIGNED_FOR_SITEID, new Object[]{siteId});
	}
}
