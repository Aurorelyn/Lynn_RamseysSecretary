package me.Aurorelyn.DiscordBots.RamseySecretary;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import org.apache.http.ParseException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Role;

public class DiscordBot {
	static JDA bot;

	final static String SERVER_ID = System.getenv("SERVER_ID");//373312450369683457l;
	final static String ROLE_ID = System.getenv("ROLE_ID");//843261776380428378l;

	public static void main(String[] args) throws LoginException, InterruptedException {
		String token = System.getenv("token");

		JDABuilder builder = JDABuilder.createDefault(token);

		bot = builder.build();
		bot.awaitReady();
		new RunningLoop().run();

	}

	public static void addRolePlRole(String userId) {
		Role plRole = bot.getRoleById(ROLE_ID);
		bot.getGuildById(SERVER_ID).addRoleToMember(userId, plRole).queue();
	}
}

class RunningLoop extends Thread {
	@Override
	public void run() {
		while (true) {
			if (QueueManager.queueLength() > 0) {
				try {
					QueueManager.validateFirstInQueue();
					Thread.sleep(60000);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}