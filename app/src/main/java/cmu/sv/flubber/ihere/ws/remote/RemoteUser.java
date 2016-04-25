package cmu.sv.flubber.ihere.ws.remote;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import cmu.sv.flubber.ihere.entities.ITag;
import cmu.sv.flubber.ihere.entities.User;

/**
 * Created by zhengyiwang on 4/13/16.
 */
public class RemoteUser {
    /*
    This class is used to interact with server
    */
    public static void loginUser(String userEmail, String password) {
        HashMap<String, String> requestData  = new HashMap<>();
        requestData.put("email", userEmail);
        requestData.put("password", password);

        final Gson gson = new Gson();

        String respond = null;
        try {
            respond = RequestHandler.getRequest(RequestHandler.GET_USER_INFO, requestData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = gson.fromJson(respond, new TypeToken<User>(){}.getType());

    }

    public static void signupUser(String userName, String userEmail, String password){}

    public static void logoutUser(String userName){}

    public static  ArrayList<ITag> getUserHistory(String userName){
        return null;
    }

    public static void updateUserName(String userEmail, String newName){}

    public static void main (String [] arg){
        loginUser("1", "1234");

    }


}
