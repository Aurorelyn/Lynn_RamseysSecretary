package me.Aurorelyn.DiscordBots.RamseySecretary;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/queue")
public class userQueue extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//QueueManager.validateFirstInQueue();
		req.setAttribute("queueLength", QueueManager.queueLength());
		
        RequestDispatcher view = getServletContext().getRequestDispatcher("/user.jsp");
        view.forward(req, resp);  
	}
}
