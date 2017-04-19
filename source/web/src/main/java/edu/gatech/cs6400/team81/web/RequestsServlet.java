package edu.gatech.cs6400.team81.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import edu.gatech.cs6400.team81.dao.Grant_DenyFoodBankRequestDAO;
import edu.gatech.cs6400.team81.dao.ItemDAO;
import edu.gatech.cs6400.team81.dao.RequestedItemDAO;
import edu.gatech.cs6400.team81.model.Item;
import edu.gatech.cs6400.team81.model.RequestedItem;
import edu.gatech.cs6400.team81.model.RequestedItemStatus;
import edu.gatech.cs6400.team81.model.Site;

public class RequestsServlet extends BaseServlet implements Servlet {

	private static final long serialVersionUID = 7568036377525758379L;

	@Autowired
	private ItemDAO itemDAO;

	@Autowired
	private RequestedItemDAO requestedItemDAO;

	@Autowired
	private Grant_DenyFoodBankRequestDAO grant_denyDAO;

	/**
	 * @see BaseServlet#BaseServlet()
	 */
	public RequestsServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Site site = getLoggedOnSite(request);
    
		if("true".equalsIgnoreCase(request.getParameter("init"))){
		
			try{		
				
				List<RequestedItem> requestedItems = requestedItemDAO.getAllForOwnSiteAndStatus(site.getId(), RequestedItemStatus.PENDING);
				for (RequestedItem requestedItem : requestedItems) {
					Item item = itemDAO.getByItemId(site.getId(), requestedItem.getItemId());
					requestedItem.setItem(item);
				}				
				
				request.setAttribute("items", requestedItems);
	
				forward("/jsp/viewFoodBankRequests.jsp", request, response);
				
			} catch (SQLException e) {
				e.printStackTrace();
				saveErrors(request, new String[]{e.toString()});
			}		
		} else if("deny".equalsIgnoreCase(request.getParameter("action"))){
			int requestId = Integer.parseInt(request.getParameter("requestId"));
			
			try {
				grant_denyDAO.denyRequest(requestId);
				
				saveMessages(request, new String[]{"Request was denied successfully."});
				
			} catch (SQLException e) {				
				e.printStackTrace();
				saveErrors(request, new String[]{e.toString()});
			}
			
			response.sendRedirect("/RequestsServlet?init=true");
		} else if("grant".equalsIgnoreCase(request.getParameter("action"))){
			int requestId = Integer.parseInt(request.getParameter("requestId"));
			int numUnits = Integer.parseInt(request.getParameter("qty"));
						
			try{
				RequestedItem requestedItem = requestedItemDAO.getByRequestId(requestId);
				grant_denyDAO.grantRequest(numUnits, requestId);
				grant_denyDAO.reduceItemCount(numUnits, requestedItem.getItemId());
				
				saveMessages(request, new String[]{"Successfully granted " + numUnits + " units."});
			} catch (SQLException e) {				
				e.printStackTrace();
				saveErrors(request, new String[]{e.toString()});
			} 
			
			response.sendRedirect("/RequestsServlet?init=true");
		}    
    }	
}
