package edu.gatech.cs6400.team81.web;

import java.io.IOException;
//import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import edu.gatech.cs6400.team81.dao.LogEntryDAO;
import edu.gatech.cs6400.team81.model.LogEntry;


/**
 *
 */
public class ViewLogEntryServlet extends BaseServlet implements Servlet {

	private static final long serialVersionUID = 3156014459741792496L;
	
	private static final Map<Integer, String> COLUMNAMES = new HashMap<Integer, String>();
	static{	
		COLUMNAMES.put(1, "Client Id");
		COLUMNAMES.put(2, "Date/Time");
		COLUMNAMES.put(3, "DescOfService");
		COLUMNAMES.put(4, "Notes");
		COLUMNAMES.put(5, "Site Id");
	}

	@Autowired
	private LogEntryDAO logEntryDAO; 
	
	/**
	 * @see BaseServlet#BaseServlet()
	 */
	public ViewLogEntryServlet() {
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
				forward("/jsp/viewLogEntries.jsp", request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			saveErrors(request, new String[] { e.toString() });
			forward("/jsp/viewLogEntries.jsp", request, response);
		}

	}	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void initView(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		List<LogEntry> log = logEntryDAO.getByClientId(Integer.parseInt(request.getParameter("clientId")));

		request.setAttribute("log", log);
	}
	
	
}
