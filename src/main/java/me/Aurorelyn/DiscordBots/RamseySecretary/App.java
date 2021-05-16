package me.Aurorelyn.DiscordBots.RamseySecretary;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/validate")
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
