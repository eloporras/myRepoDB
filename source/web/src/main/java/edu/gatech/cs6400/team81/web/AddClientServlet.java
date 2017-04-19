package edu.gatech.cs6400.team81.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;


import edu.gatech.cs6400.team81.dao.ClientDAO;
import edu.gatech.cs6400.team81.dao.LogEntryDAO;
import edu.gatech.cs6400.team81.model.Client;
import edu.gatech.cs6400.team81.model.DescOfService;
import edu.gatech.cs6400.team81.model.LogEntry;
import edu.gatech.cs6400.team81.model.Site;

/**
 * Servlet implementation class HomeServlet
 */
public class AddClientServlet extends BaseServlet implements Servlet {	
	private static final long serialVersionUID = 4961433641181096163L;

	@Autowired
	private ClientDAO clientDAO;
	
	@Autowired 
	private LogEntryDAO logEntryDAO;
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Site site = getLoggedOnSite(request);

		if("true".equalsIgnoreCase(request.getParameter("init"))){
			try {
				int clientId = Integer.parseInt(request.getParameter("clientId"));
				
				Client client = clientDAO.getByIdentifier(clientId);	
				
				request.setAttribute("clientId", client.getIdentifier());
				request.setAttribute("desc", client.getDescription());
				request.setAttribute("name", client.getName());
				request.setAttribute("phone", client.getPhone());				
	
				forward("/jsp/addClient.jsp?init=false?action=Edit", request, response);
			} catch (SQLException e) {
				e.printStackTrace();
				forward("/jsp/addClient.jsp", request, response, new String[]{e.getMessage()});
			}
		} else if("NewClient".equalsIgnoreCase(request.getParameter("action"))){
			response.sendRedirect("/jsp/addClient.jsp");
		} else if("AddClient".equalsIgnoreCase(request.getParameter("action"))){
			try {
				String desc = request.getParameter("desc");
				String name = request.getParameter("name");
				String phone = request.getParameter("phone");
				
				int clientId = clientDAO.createClient(desc, name, phone);
				
				saveMessages(request, new String[]{"Added client: " + name + "." });
				LogEntry logEntry = new LogEntry(clientId, site.getId(), DescOfService.ADDED_CLIENT.toString());
				logEntryDAO.add(logEntry);

				response.sendRedirect("/jsp/addClient.jsp");
			} catch (NumberFormatException e) {
				e.printStackTrace();
				saveErrors(request, new String[]{e.getMessage()});
				response.sendRedirect("/jsp/addClient.jsp");					
			} catch (SQLException e) {
				e.printStackTrace();
				saveErrors(request, new String[]{e.getMessage()});
				response.sendRedirect("/jsp/addClient.jsp");					
			}
		} else if("EditClient".equalsIgnoreCase(request.getParameter("action"))){
			try{
				Integer clientId = Integer.parseInt(request.getParameter("clientId"));
				
				Client client = new Client();			
				client.setIdentifier(clientId);
				client.setDescription(request.getParameter("desc"));
				client.setName(request.getParameter("name"));
				client.setPhone(request.getParameter("phone"));	
				
				String msg = clientDAO.update(client);
				
				if (!msg.isEmpty()){
					 LogEntry logEntry = new LogEntry(clientId, site.getId(), DescOfService.CHANGED_CLIENT_DATA.toString(), msg);
					 logEntryDAO.add(logEntry);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				saveErrors(request, new String[]{e.getMessage()});
				response.sendRedirect("/jsp/addClient.jsp");					
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
