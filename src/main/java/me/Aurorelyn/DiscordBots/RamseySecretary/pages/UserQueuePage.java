package me.Aurorelyn.DiscordBots.RamseySecretary.pages;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.Aurorelyn.DiscordBots.RamseySecretary.backgroundManagers.QueueManager;

/**
 *   This class is responsible for serving the /queue page 
 */
public class UserQueuePage extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6231119129069299772L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * Called when /queue page is accessed.
		 */
		req.setAttribute("queueLength", QueueManager.queueLength());
		
        RequestDispatcher view = getServletContext().getRequestDispatcher("/user.jsp");
        view.forward(req, resp);  
	}
}
