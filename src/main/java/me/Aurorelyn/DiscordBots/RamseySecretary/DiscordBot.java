package me.Aurorelyn.DiscordBots.RamseySecretary;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import me.Aurorelyn.DiscordBots.RamseySecretary.backgroundManagers.QueueManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Role;

/**
* The Ramsey's Secretary is a discord OAuth implementation that
* automates the verification of a user's pirate legend status.
*
* @author  Aurorelyn (Aurorelyn#4695)
* @version 1.0
* @since   05-21-2021
* 
*/

/*
 * Acts as entry class for the project. Starts and manages the discord bot and user queue
 */
public class DiscordBot implements ServletContextListener {
	static JDA bot;

	final static String SERVER_ID = System.getenv("SERVER_ID");
	final static String ROLE_ID = System.getenv("ROLE_ID");

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		/*
		 * Functions as main method, runs on project startup
		 */
		try {
			String token = System.getenv("token");

			JDABuilder builder = JDABuilder.createDefault(token);
			bot = builder.build();
			bot.awaitReady();
			new RunningLoop().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServletContextListener.super.contextInitialized(sce);
	}

	public static void addRolePlRole(String userId) {
		Role plRole = bot.getRoleById(ROLE_ID);
		bot.getGuildById(SERVER_ID).addRoleToMember(userId, plRole).queue();
	}
}

class RunningLoop extends Thread {
	/*
	 * Every minute verify the next user in queue. If queue is empty, wait 5 seconds before checking again.
	 */
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