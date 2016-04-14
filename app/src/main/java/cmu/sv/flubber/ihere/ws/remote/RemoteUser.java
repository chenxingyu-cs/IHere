package cmu.sv.flubber.ihere.ws.remote;

import java.util.ArrayList;

import cmu.sv.flubber.ihere.entities.ITag;

/**
 * Created by zhengyiwang on 4/13/16.
 */
public class RemoteUser {
    /*
    This class is used to interact with server
    */
    public static void loginUser(String userEmail, String password){}

    public static void signupUser(String userName, String userEmail, String password){}

    public static void logoutUser(String userName){}

    public static  ArrayList<ITag> getUserHistory(String userName){
        return null;
    }

    public static void updateUserName(String userEmail, String newName){}




}
