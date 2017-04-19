package edu.gatech.cs6400.team81.web;

import java.io.IOException;
//import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import edu.gatech.cs6400.team81.dao.ClientDAO;
import edu.gatech.cs6400.team81.dao.LogEntryDAO;
import edu.gatech.cs6400.team81.model.Client;
import edu.gatech.cs6400.team81.model.LogEntry;
import edu.gatech.cs6400.team81.model.Site;


/**
 *
 */
public class ClientServlet extends BaseServlet implements Servlet {

	private static final long serialVersionUID = 3156014459741792496L;
	
	//Set the available search criteria
	private static final int DESCRIPRION 	= 1;
	private static final int NAME	= 2;



	private static final Map<Integer, String> COLUMNAMES = new HashMap<Integer, String>();
	static{	
		COLUMNAMES.put(1, "Description");
		COLUMNAMES.put(2, "Name");
	}

	@Autowired
	private ClientDAO clientDAO;
	
	@Autowired
	private LogEntryDAO logEntryDAO; 
	
	/**
	 * @see BaseServlet#BaseServlet()
	 */
	public ClientServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if("true".equalsIgnoreCase(request.getParameter("init"))){
				initView(request, response);
				forward("/jsp/manageClient.jsp", request, response);
			}else if ("checkIn".equalsIgnoreCase(request.getParameter("action"))) {
				forward("/jsp/manageClient.jsp", request, response);
			}else if ("search".equalsIgnoreCase(request.getParameter("action"))) {
				search(request, response);
			} else if ("doCheckin".equalsIgnoreCase(request.getParameter("action"))){
				handleCheckinAJAX(request, response);
			} else if ("doClientUpdate".equalsIgnoreCase(request.getParameter("action"))){
				handleCliengtUpdateAJAX(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			saveErrors(request, new String[] { e.toString() });
			forward("/jsp/manageClient.jsp", request, response);
		}

	}

	private void handleCliengtUpdateAJAX(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			int id = Integer.parseInt(request.getParameter("clientId"));
			Client client = clientDAO.getByIdentifier(id);
			Site site = getLoggedOnSite(request);
			if(client != null){
				client.setDescription(request.getParameter("desc"));
				client.setName(request.getParameter("name"));
				client.setPhone(request.getParameter("phone"));
				String notes = clientDAO.update(client);

				if(StringUtils.isNotBlank(notes)){
					LogEntry logEntry = new LogEntry(id, site.getId(), "Client Update", notes);
					logEntryDAO.add(logEntry);
				}
				
				response.getWriter().write("Success");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());			
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		}
	}

	private void handleCheckinAJAX(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			int clientId = Integer.parseInt(request.getParameter("clientId"));
			Site site = getLoggedOnSite(request);
			String serviceType = request.getParameter("serviceType");
			String notes = request.getParameter("notes");
			
			LogEntry logEntry = new LogEntry(clientId, site.getId(), "Client Checkin to " + site.getShortName() + " " + serviceType, notes);
			if(logEntryDAO.add(logEntry)){	
				response.getWriter().write("Success");
			} else {
				response.getWriter().write("Could not save LogEntry");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void search(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<String> sqlCriteria = new ArrayList<String>();
		int criteriaIndex = 1;

		while (request.getParameter("sel-criteria-" + criteriaIndex) != null) {
			int columnKey = Integer.parseInt(request.getParameter("sel-criteria-" + criteriaIndex));
			if (columnKey >= DESCRIPRION && columnKey <= NAME) {
				try {
					String columnName = COLUMNAMES.get(columnKey);
					String value = request.getParameter("sel-value-" + criteriaIndex);
					String matchType = request.getParameter("sel-selector-" + criteriaIndex);
					String connector = request.getParameter("sel-connect-" + criteriaIndex);
					StringBuilder clause = new StringBuilder();
					switch (columnKey) {
					case DESCRIPRION:
						clause.append("c.").append(columnName);
						MATCHTYPE type = MATCHTYPE.valueOf(matchType);
						if(type == MATCHTYPE.MATCH){
							clause.append(" = '").append(value).append("' ");
						} else {
							clause.append(" LIKE '%").append(value).append("%' ");
						}
						break;
					case NAME:
						clause.append("c.").append(columnName);
						type = MATCHTYPE.valueOf(matchType);
						if(type == MATCHTYPE.MATCH){
							clause.append(" = '").append(value).append("' ");
						} else {
							clause.append(" LIKE '%").append(value).append("%' ");
						}
						break;
					}

					if (connector != null) {
						clause.append(" ").append(CONNECTOR.valueOf(connector).toString()).append(" ");
					}

					sqlCriteria.add(clause.toString());
//				} catch (ParseException e) {
//					e.printStackTrace();
				}catch (Exception e){
				}
				criteriaIndex++;
			}
		}
		
		StringBuilder whereClause = new StringBuilder(" WHERE "); 
		for (String string : sqlCriteria) {
			whereClause.append(string);
		}
		
		System.out.println(whereClause);
				
		try {
			List<Client> clients = clientDAO.search(whereClause.toString());
			
			//Make sure the results are less than 5.
			if (clients.size() > 4){
				throw new Exception("Your search returned too many results.  Please refine your criteria.");
			}
			
			ObjectMapper mapper = new ObjectMapper();
			//TODO check length.
			response.setContentType("application/json");
			response.getWriter().write(mapper.writeValueAsString(clients));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		} catch (JsonMappingException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		}
	}

	private void initView(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		request.setAttribute("checkInType", request.getParameter("checkInType"));
	}
	
	private enum CONNECTOR{
		AND, OR;
	}
	
	private enum MATCHTYPE{
		MATCH, LIKE;	
	}
	
}
