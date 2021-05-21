package me.Aurorelyn.DiscordBots.RamseySecretary.backgroundManagers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class converts an OAuth code response into usable JSON user data
 */

public class CodeHandler {

	/* Discord OAuth id and secret from environmental variables set on the server */
	private static final String CLIENT_ID = System.getenv("OAUTH_CLIENT_ID");
	private static final String CLIENT_SECRET = System.getenv("OAUTH_CLIENT_SECRET");
	
	private static final String REDIRECT_URL = "https://ramseyssecretary.herokuapp.com/validate";
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
		/*
		 * Convert oauth code to user data by parsing discord user api.
		 */

		JSONObject tokenJSON = requestTokenFromCode(code);                       //Use resulting code from OAuth process to generate request token
	
		JSONObject discordIdentityJSON = requestIdentityUsingToken(tokenJSON);   //Use resulting token to request discord identity
		
		JSONArray connectionsJSONArray = requestConnectionsUsingToken(tokenJSON);//Use resulting token to request discord connections
           
        JSONObject xboxJson = null;                                              //**
        for (Object object : connectionsJSONArray) {                             //
			JSONObject jsonObject = new JSONObject(object.toString());           //
			if(jsonObject.get("type").toString().equalsIgnoreCase("xbox")) {     // Loop through connections list and extract 
				xboxJson = jsonObject;                                           // xbox connection.
			}                                                                    //
		}                                                                        //**
        
        if( xboxJson == null ) throw new NullPointerException("No xbox connection found");   //If no xbox connection is found, throw null pointer
        

		return new CodeHandler(discordIdentityJSON, xboxJson);
		
	}

	private static JSONArray requestConnectionsUsingToken(JSONObject tokenJSON) throws ParseException, IOException {
		/*
		 * Make get request to discord connection scope with token authentication. Return result as json.
		 */
        String discConnectionsUrl = "https://discord.com/api/users/@me/connections";
        List<NameValuePair> authHeaders = new ArrayList<>();
        authHeaders.add(new BasicNameValuePair("authorization", tokenJSON.get("token_type") + " " + tokenJSON.get("access_token")));
		
        String userDiscordConnections = WebRequestManager.doGetRequest(discConnectionsUrl, authHeaders);
        JSONArray connectionsJSONArray = new JSONArray(userDiscordConnections);
        
        return connectionsJSONArray;
	}

	private static JSONObject requestIdentityUsingToken(JSONObject tokenJSON) throws ParseException, IOException {
		/*
		 * Make get request to discord identity scope with token authentication. Return result as json.
		 */
		String discIdentityUrl = "https://discord.com/api/users/@me";
        List<NameValuePair> authHeaders = new ArrayList<>();
        authHeaders.add(new BasicNameValuePair("authorization", tokenJSON.get("token_type") + " " + tokenJSON.get("access_token")));
		
        String userDiscordIdentiy = WebRequestManager.doGetRequest(discIdentityUrl, authHeaders);
        JSONObject discordIdentityJSON = new JSONObject(userDiscordIdentiy);
        
        return discordIdentityJSON;
	}

	private static JSONObject requestTokenFromCode(String code) throws IOException {
		/*
		 * Make Post request to discord to get token using code. Return result as json.
		 */
		String discTokenUrl = "https://discord.com/api/oauth2/token";

        List<NameValuePair> tokenUrlParameters = new ArrayList<>();
        tokenUrlParameters.add(new BasicNameValuePair("client_id", CLIENT_ID));
        tokenUrlParameters.add(new BasicNameValuePair("client_secret", CLIENT_SECRET));
        tokenUrlParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        tokenUrlParameters.add(new BasicNameValuePair("code", code));
        tokenUrlParameters.add(new BasicNameValuePair("redirect_uri", REDIRECT_URL));
		
		String userTokenResponse = WebRequestManager.doPostRequest(discTokenUrl, tokenUrlParameters);
		JSONObject tokenJSON = new JSONObject(userTokenResponse);
        
		return tokenJSON;
	}
	

}
