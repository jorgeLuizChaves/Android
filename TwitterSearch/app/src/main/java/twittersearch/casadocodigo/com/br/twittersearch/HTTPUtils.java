package twittersearch.casadocodigo.com.br.twittersearch;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by Jorge on 2/11/15.
 */
public class HTTPUtils {

    private static final int OK = 200;
    private static final String ENCODE_UTF8 = "UTF-8";
    final static String CONSUMER_KEY = "2UHdsGb3LbzI64JS0ASaJqoh6";
    final static String TwitterTokenURL = "https://api.twitter.com/oauth2/token";
    final static String CONSUMER_SECRET = "4NjiJX7hvN5giKmXzZQTTfG4HcOdwqNWlR8Nbtt8WpGxw3De7P";
    final static String TwitterStreamURL = "https://api.twitter.com/1.1/search/tweets.json?q=";

    private String getResponseBody(HttpRequestBase request) {
        StringBuilder sb = new StringBuilder();
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpResponse response = httpClient.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();
            String reason = response.getStatusLine().getReasonPhrase();

            if (statusCode == OK) {

                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();

                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, ENCODE_UTF8), 8);
                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sb.append(line);
                }
            } else {
                sb.append(reason);
            }
        } catch (UnsupportedEncodingException ex) {
        } catch (ClientProtocolException ex1) {
        } catch (IOException ex2) {
        }
        return sb.toString();
    }

    // convert a JSON authentication object into an Authenticated object
    private Authenticated jsonToAuthenticated(String rawAuthorization) {
        Authenticated auth = null;
        if (rawAuthorization != null && rawAuthorization.length() > 0) {
            try {
                Gson gson = new Gson();
                auth = gson.fromJson(rawAuthorization, Authenticated.class);
            } catch (IllegalStateException ex) {
                Log.e("error log", ex.getMessage());
            }
        }
        return auth;
    }

    public Authenticated authenticateApp(){
        try {
            String urlApiKey = URLEncoder.encode(CONSUMER_KEY, ENCODE_UTF8);
            String urlApiSecret = URLEncoder.encode(CONSUMER_SECRET, ENCODE_UTF8);
            String combined = String.format("%s:%s", urlApiKey, urlApiSecret);
            String base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);

            HttpPost httpPost = new HttpPost(TwitterTokenURL);
            httpPost.setHeader("Authorization", "Basic " + base64Encoded);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpPost.setEntity(new StringEntity("grant_type=client_credentials"));
            String rawAuthorization = getResponseBody(httpPost);
            Authenticated auth = jsonToAuthenticated(rawAuthorization);
            return auth;
        } catch (UnsupportedEncodingException e) {
            Log.e("error log","");
        }
        return null;
    }

    public String getTwitterStream(Authenticated auth, String screenName) {
            if (auth != null && auth.token_type.equals("bearer")) {

                HttpGet httpGet = new HttpGet(TwitterStreamURL + screenName);

                httpGet.setHeader("Authorization", "Bearer " + auth.access_token);
                httpGet.setHeader("Content-Type", "application/json");
                // update the results with the body of the response
                String results = getResponseBody(httpGet);
                return results;
            }
        return null;
    }
}