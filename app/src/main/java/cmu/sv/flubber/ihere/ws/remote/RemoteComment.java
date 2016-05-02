package cmu.sv.flubber.ihere.ws.remote;

/**
 * Created by zhengyiwang on 4/13/16.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;

import cmu.sv.flubber.ihere.entities.Comment;


public class RemoteComment {
    /*
    this class is used to interact with server for commment data update
     */

    public static  Comment getComment(int commentId){
        return null;
    }

    public static boolean addComment(Comment comment){
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String s = gson.toJson(comment);
        HashMap<String, String> requestData  = new HashMap<>();
        requestData.put("comment", s);

        String respond = null;

        try {
            respond = RequestHandler.getRequest(RequestHandler.CREATE_NEW_COMMENT, requestData);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static void updateComment(Comment comment){}

    public  static boolean deleteComment(int commentId){
        HashMap<String, String> requestData  = new HashMap<>();
        requestData.put("iTagId", String.valueOf(commentId));

        String respond = null;

        try {
            respond = RequestHandler.getRequest(RequestHandler.CREATE_NEW_ITAG, requestData);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
