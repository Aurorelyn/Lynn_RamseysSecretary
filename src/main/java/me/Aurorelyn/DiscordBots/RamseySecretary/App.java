package me.Aurorelyn.DiscordBots.RamseySecretary;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(urlPatterns = "/validate")
public class App extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String code = req.getParameter("code");
		
		if(!(code == null || code == "")) {
			
			try {
				CodeHandler handler = CodeHandler.handleCode(code);
				req.setAttribute("sourcedXboxName", handler.getXboxData().get("name"));
				QueueManager.addToQueue(handler);
			} catch (Exception e) {
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
