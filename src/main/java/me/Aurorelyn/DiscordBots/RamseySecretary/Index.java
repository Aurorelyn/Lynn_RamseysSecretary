package me.Aurorelyn.DiscordBots.RamseySecretary;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@WebServlet(urlPatterns = "/")
public class Index extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			DiscordBot.main(null);
		} catch (LoginException | InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}
