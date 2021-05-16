package me.Aurorelyn.DiscordBots.RamseySecretary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class CodeHandler {

	private static final String CLIENT_ID = "837022531378085930";
	private static final String CLIENT_SECRET = "Wl35q85ZnEqOn5bgrgTkW1QRGmTBZA7K";
	private static final String REDIRECT_URL = "http://localhost:8080/PirateLegendValidator/validate";
	private JSONObject discordData;
	private JSONObject xboxData;
	
	public JSONObject getDiscordData() {
		return discordData;
	}

	public void setDiscordData(JSONObject discordData) {
		this.discordData = discordData;
	}

	public JSONObject getXboxData() {
		return xboxData;
	}

	public void setXboxData(JSONObject xboxData) {
		this.xboxData = xboxData;
	}


	
	public CodeHandler(JSONObject discordData_, JSONObject xboxData_) {
		discordData = discordData_;
		xboxData = xboxData_;
	}
	
	public static CodeHandler handleCode(String code) throws IOException, InterruptedException {
		
		String discUrl = "https://discord.com/api/oauth2/token";
		HttpPost post = new HttpPost(discUrl);
		

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("client_id", CLIENT_ID));
        urlParameters.add(new BasicNameValuePair("client_secret", CLIENT_SECRET));
        urlParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        urlParameters.add(new BasicNameValuePair("code", code));
        urlParameters.add(new BasicNameValuePair("redirect_uri", REDIRECT_URL));
		
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		
		
		String result;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(post)) {
               result = EntityUtils.toString(response.getEntity());
           }
		
        JSONObject tokenJSON = new JSONObject(result);
        
        HttpGet get = new HttpGet("https://discord.com/api/users/@me");
        get.addHeader("authorization", tokenJSON.get("token_type") + " " + tokenJSON.get("access_token"));
        
        
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(get)) {
               result = EntityUtils.toString(response.getEntity());
           }
        
        JSONObject discordJSON = new JSONObject(result);
        
        get = new HttpGet("https://discord.com/api/users/@me/connections");
        get.addHeader("authorization", tokenJSON.get("token_type") + " " + tokenJSON.get("access_token"));
        
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(get)) {
               result = EntityUtils.toString(response.getEntity());
           }
        
        JSONArray xboxJSONArray = new JSONArray(result);
        
        JSONObject xboxJson = null;
        for (Object object : xboxJSONArray) {
			JSONObject jsonObject = new JSONObject(object.toString());
			if(jsonObject.get("type").toString().equalsIgnoreCase("xbox")) {
				xboxJson = jsonObject;
			}
		}
        
        System.out.println(xboxJson);
        
		return new CodeHandler(discordJSON, xboxJson);
		
	}
	

}
