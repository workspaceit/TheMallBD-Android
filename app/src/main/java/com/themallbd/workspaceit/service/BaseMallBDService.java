package com.themallbd.workspaceit.service;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rajib on 2/11/16.
 */
public class BaseMallBDService {


    private String baseURL="http://188.166.214.41/mallbdweb_develop/public/"; //new URL
    //private String baseURL="http://188.166.214.41/mallbdweb/public/index.php/";//riyad vai new server
    //private String baseURL = "http://163.53.151.2:5555/mallbdweb/public/index.php";//rafi vai server
    // private String baseURL = "http://192.168.1.11/mallbdweb/public/index.php/";//local

    public static int shop_id = 1;

    private String controller;
    public String responseMsg;
    public boolean status;
    private static String sCookie;


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
                System.out.println(baseURL + this.controller);
                URL url = new URL(baseURL + this.controller);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                if (sCookie != null && sCookie.length() > 0) {
                    connection.setRequestProperty("Cookie", sCookie);
                }

                System.out.println("GET");
                connection.setRequestMethod(method);
                int responseCode = connection.getResponseCode();
                System.out.println("GET Response Code :: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) { // success

                    String cookie = connection.getHeaderField("set-cookie");
                    if (cookie != null && cookie.length() > 0) {
                        sCookie = cookie;
                    }
                    return readStream(connection.getInputStream());
                } else {
                    System.out.println("GET request not worked");
                }
            } else if (method.equalsIgnoreCase("POST")) {


                System.out.println(baseURL + this.controller);
                URL obj = new URL(baseURL + this.controller);

                HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                if (sCookie != null && sCookie.length() > 0) {
                    connection.setRequestProperty("Cookie", sCookie);
                }
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
                    String cookie = connection.getHeaderField("set-cookie");
                    if (cookie != null && cookie.length() > 0) {
                        sCookie = cookie;
                    }
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
