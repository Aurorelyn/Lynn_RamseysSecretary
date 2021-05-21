package me.Aurorelyn.DiscordBots.RamseySecretary.backgroundManagers;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/*
 * This class manages get/post requests. Returns results as string.
 */
public class WebRequestManager {
	public static String doGetRequest(String url, List<NameValuePair> identityHeaders) throws ParseException, IOException {
        HttpGet get = new HttpGet(url);
        
        for (NameValuePair header : identityHeaders) {
			get.addHeader(header.getName(), header.getValue());
		}
        
        
        String result;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(get)) {
               result = EntityUtils.toString(response.getEntity());
           }
        
        return result;
	}

	public static String doPostRequest(String url, List<NameValuePair> urlParameters) throws IOException {
		
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		
		
		String result;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(post)) {
               result = EntityUtils.toString(response.getEntity());
           }
		
        return result;
	}
}
