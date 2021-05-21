package me.Aurorelyn.DiscordBots.RamseySecretary;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(urlPatterns = "/queue")
public class userQueue extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//QueueManager.validateFirstInQueue();
		req.setAttribute("queueLength", QueueManager.queueLength());
		
        RequestDispatcher view = getServletContext().getRequestDispatcher("/user.jsp");
        view.forward(req, resp);  
	}
}
