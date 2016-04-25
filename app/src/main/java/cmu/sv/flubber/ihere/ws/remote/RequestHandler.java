package cmu.sv.flubber.ihere.ws.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


/**
 * Created by zhengyiwang on 4/24/16.
 */
public class RequestHandler {

    private static final String SERVER = "http://172.29.92.156:8080";
    public static final int GET_USER_INFO = 1;
    public static final int  CREATE_NEW_USER = 2;
    public static final int UPDATE_USER_INFO = 3;
    public static final int GET_ALL_ITAGS_BY_USER_ID = 4;
    public static final int CREATE_NEW_ITAG = 5;
    public static final int DISCOVER_ITAGS_AROUND = 6;
    public static final int UPDATE_ITAG_INFO = 7;
    public static final int DELETE_ITAG = 8;
    public static final int GET_ALL_COMMENT_BY_ITAG_ID = 9;
    public static final int CREATE_NEW_COMMENT = 10;
    public static final int UPDATE_COMMENT_INFO = 11;
    public static final int DELETE_COMMENT = 12;



    public static String getRequest(int qid, HashMap<String, String> requestData) throws IOException{

        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(SERVER).append("/IHereServer/IHereQuery?queryId=").append(String.valueOf(qid));

        for( String key : requestData.keySet()){
            requestBuilder.append("&").append(key).append("=").append(requestData.get(key));
        }

        String request = requestBuilder.toString();

        log(request);

        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(request);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            if(connection.getResponseCode() != 200) {
                throw new IOException(connection.getResponseMessage());
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String s;
            while((s = reader.readLine()) != null) {

                result.append(s);
            }
            reader.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        log(result.toString());

        return result.toString();
    }


    public static void log (String s ){
        System.out.println(s);
    }


    public static void main(String [] a ){

        log("Starting test---------");

        HashMap<String, String > r= new HashMap<>();
        r.put("email", "1");
        r.put("password", "1234");

        try {
            String result = getRequest(1, r);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


