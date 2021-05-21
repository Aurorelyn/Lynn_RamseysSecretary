package me.Aurorelyn.DiscordBots.RamseySecretary.pages;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.Aurorelyn.DiscordBots.RamseySecretary.backgroundManagers.CodeHandler;
import me.Aurorelyn.DiscordBots.RamseySecretary.backgroundManagers.QueueManager;

/**
 *   This class is responsible for serving the /validate/ page 
 *   and managing the resulting code response from discord OAuth
 */
public class ValidatePage extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5130707304620754763L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		/*
		 * Called when /validate page is accessed.
		 */
		
		String code = req.getParameter("code");  //Retrieve code from URL parameters
		
		if(!(code == null || code == "")) {
			/**
			 *  Check if the code is null or empty.
			 *  If not, use it to create CodeHandler object.
			 */
			
			try {
				
				CodeHandler userHandler = CodeHandler.handleCode(code);                      //Create CodeHandler from OAuth code.
				req.setAttribute("sourcedXboxName", userHandler.getXboxData().get("name"));  //Set the web attribute '${sourcedXboxName}' to the users xbox gamertag
				QueueManager.addToQueue(userHandler);                                        //Add the userHandler to the queue for verification
				
			} catch (Exception e) {
				
				/*
				 * If any error occurss, print to console and redirect user to the error page.
				 */
				e.printStackTrace();
				RequestDispatcher view = getServletContext().getRequestDispatcher("/error.jsp");
				view.forward(req, resp);
				return;
				
			}
			req.removeAttribute("code");
		}
		
        RequestDispatcher view = getServletContext().getRequestDispatcher("/validate.jsp");
        view.forward(req, resp);  
	}
}
