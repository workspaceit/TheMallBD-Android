package com.workspaceit.themallbd.service;


import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rajib on 2/11/16.
 */
public class BaseMallBDService {

    // private String baseURL = "http://27.147.149.178:9030/mallbdweb/public/index.php/";//local
     private String baseURL = "http://192.168.1.11/mallbdweb/public/index.php/";//local
   // private String baseURL = "http://cabguardpro.com/";//server
    public static int shop_id = 1;

    private String controller;
    public String responseMsg;
    public boolean status;


    protected Map<String, String> getPostParams;

    public BaseMallBDService() {
        this.controller = "";
        this.responseMsg = "";
        this.status = false;
        this.getPostParams = new HashMap<>();
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    protected void setParams(Map<String, String> getPostParams) {
        this.getPostParams = getPostParams;
    }

    protected void setParams(String key, String value) {
        this.getPostParams.put(key, value);
    }

    protected String getData(String method) {


        String dataAsString = "";
        try {

            if (method.endsWith("GET")) {
                URL url = new URL(baseURL+this.controller);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                System.out.println("GET");
                connection.setRequestMethod(method);
                int responseCode = connection.getResponseCode();
                System.out.println("GET Response Code :: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) { // success

                    // print result
                    return readStream(connection.getInputStream());
                } else {
                    System.out.println("GET request not worked");
                }
            }
            else if (method.equalsIgnoreCase("POST")) {

                URL obj = new URL(baseURL+this.controller);
                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                connection.setRequestMethod(method);

                String urlParameters = getPostURLEncodedParams();

                connection.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();
                int responseCode = connection.getResponseCode();
                System.out.println("POST Response Code :: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) { // success

                    // print result
                    return readStream(connection.getInputStream());
                } else {
                    System.out.println("POST request not worked");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataAsString;
    }
    private static String readStream(InputStream in) throws IOException {

        StringBuilder sb = new StringBuilder();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(in));

            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        reader.close();

        return sb.toString();
    }

    protected String getPostURLEncodedParams() {
        StringBuilder sb = new StringBuilder();
        String value = "";
        for (String key : this.getPostParams.keySet()) {
            try {
                value = URLEncoder.encode(this.getPostParams.get(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(key).append("=").append(value);
        }
        return sb.toString();
    }



}
