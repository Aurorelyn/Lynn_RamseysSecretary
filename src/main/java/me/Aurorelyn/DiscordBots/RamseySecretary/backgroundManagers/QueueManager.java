package me.Aurorelyn.DiscordBots.RamseySecretary.backgroundManagers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import me.Aurorelyn.DiscordBots.RamseySecretary.DiscordBot;

/*
 * This class manages the user queue and validates accounts eligibility for the PL Role
 */
public class QueueManager {
	
    /* Keep track of all users yet to be validated */
	private static Map<String, CodeHandler> userQueue = new HashMap<String, CodeHandler>(); 
	private static final String XAUTH_ID = System.getenv("XAUTH_ID");
	
	public static String addToQueue(CodeHandler handler) {
		/*
		 * Add user to the queue using their corresponding CodeHandler
		 */
		String id = handler.getXboxData().getString("id"); //Retrieve discord connection ID for xbox connection
		
		if(!userQueue.containsKey(id)) { //Make sure the user is not already in the queue
			userQueue.put(id, handler); //Add user to the queue.
		}
		return id; //Return user identifier for later access
	}
	
	public static void validateFirstInQueue() throws ParseException, IOException{
		/*
		 * Get first user in queue and check their eligibility for the pl role.
		 */
		String firstKey = (String) userQueue.keySet().toArray()[0];
		CodeHandler user = userQueue.get(firstKey);
		
		if(isPirateLegend(user)) { //If user is a 'pirate legend', tell the discord bot to add the role to the provided discord user id
			DiscordBot.addRolePlRole(user.getDiscordData().getString("id"));
		}
		userQueue.remove(firstKey); //Remove the user from the queue
		
	}
	
	private static boolean isPirateLegend(CodeHandler user) throws ParseException, IOException {
		/*
		 * Use X API to verify a user's Pirate Legend Status
		 */
		String name = user.getXboxData().getString("name");
		
		String xuidRequestUrl = "https://xapi.us/v2/xuid/" + name;
		
		List<NameValuePair> authHeaders = new ArrayList<>();
	    authHeaders.add(new BasicNameValuePair("X-Auth", XAUTH_ID));
        
        String xuid = WebRequestManager.doGetRequest(xuidRequestUrl, authHeaders); // Convert user gamertag to XUID
        
        
        String getAchievementsUrl = "https://xapi.us/v2/"+ xuid + "/achievements/1717113201";
        
        String achivementArray = WebRequestManager.doGetRequest(getAchievementsUrl, authHeaders);
        
        JSONArray achievementJSONArray = new JSONArray(achivementArray);  //Get all user sea of thieves achievements as JSON array
        
        /*
         * Loop through all user's sea of thieves achievements, if they have the "Become Pirate Legend" achievement (acID: 29) 
         * Return true, otherwise return false
         */
        for (Object object : achievementJSONArray) {
			JSONObject jsonObject = new JSONObject(object.toString());
			if(jsonObject.get("id").toString().equalsIgnoreCase("29")) {
				if(jsonObject.getString("progressState").equalsIgnoreCase("Achieved")) {
					return true;
				}else {
					return false;
				}
			}
		}
        
        return false;
	}

	public static int queueLength() {
		return userQueue.size();
	}
	

}
