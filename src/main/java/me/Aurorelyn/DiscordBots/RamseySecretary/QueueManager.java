package me.Aurorelyn.DiscordBots.RamseySecretary;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class QueueManager {

	private static Map<String, CodeHandler> userQueue = new HashMap<String, CodeHandler>();
	
	public static String addToQueue(CodeHandler handler) {	
		String id = handler.getXboxData().getString("id");
		
		if(!userQueue.containsKey("id")) {
			userQueue.put(id, handler);
		}
		return handler.getXboxData().getString("id");
	}
	
	public static void validateFirstInQueue() throws ParseException, IOException{
		String firstKey = (String) userQueue.keySet().toArray()[0];
		CodeHandler user = userQueue.get(firstKey);
		
		if(isPirateLegend(user)) {
			DiscordBot.addRolePlRole(user.getDiscordData().getString("id"));
		}
		userQueue.remove(firstKey);
		
	}
	
	private static boolean isPirateLegend(CodeHandler user) throws ParseException, IOException {
		
		String name = user.getXboxData().getString("name");
		
        HttpGet getXUID = new HttpGet("https://xapi.us/v2/xuid/" + name);
        getXUID.addHeader("X-Auth", "b23c5a7532827503886fe1d9fec344a7c0e73358");
        
        String result;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(getXUID)) {
               result = EntityUtils.toString(response.getEntity());
           }
        
        String xuid = result;
        
        HttpGet getAchievements = new HttpGet("https://xapi.us/v2/"+ xuid + "/achievements/1717113201");
        getAchievements.addHeader("X-Auth", "b23c5a7532827503886fe1d9fec344a7c0e73358");
        
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(getAchievements)) {
               result = EntityUtils.toString(response.getEntity());
           }
        
        JSONArray achievementJSONArray = new JSONArray(result);
        
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
