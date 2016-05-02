package cmu.sv.flubber.ihere.ws.remote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import cmu.sv.flubber.ihere.entities.Comment;
import cmu.sv.flubber.ihere.entities.ITag;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Created by zhengyiwang on 4/13/16.
 */
public class RemoteItag {
    /*
    This class is used to interact with server to get update itag data
     */
    public static boolean createItag(ITag iTag){
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String s = gson.toJson(iTag);
        HashMap<String, String> requestData  = new HashMap<>();
        requestData.put("itag", s);

        String respond = null;

        try {
            respond = RequestHandler.getRequest(RequestHandler.CREATE_NEW_ITAG, requestData);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public static ArrayList<ITag> discoverItags(double longitude, double latitude, String  direction)  {
        HashMap<String, String> requestData  = new HashMap<>();
        requestData.put("longitude", String.valueOf(longitude));
        requestData.put("latitude", String.valueOf(latitude));
        requestData.put("direction", String.valueOf(direction));

        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        String respond = null;
        try {
            respond = RequestHandler.getRequest(RequestHandler.DISCOVER_ITAGS_AROUND, requestData);
        } catch (IOException e) {
            e.printStackTrace();
        }


        ArrayList<ITag> iTagList = gson.fromJson(respond, new TypeToken<ArrayList<ITag>>(){}.getType());

        return iTagList;
    }

    public static ArrayList<ITag> getAllItagsByUserId(int userid)  {
        HashMap<String, String> requestData  = new HashMap<>();
        requestData.put("userId", String.valueOf(userid));

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        String respond = null;
        try {
            respond = RequestHandler.getRequest(RequestHandler.GET_ALL_ITAGS_BY_USER_ID, requestData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<ITag> iTagList = gson.fromJson(respond, new TypeToken<ArrayList<ITag>>(){}.getType());

        return iTagList;
    }

    public static ITag getITagById(int itagId)  {
        HashMap<String, String> requestData  = new HashMap<>();
        requestData.put("iTagId", String.valueOf(itagId));

        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        String respond = null;
        try {
            respond = RequestHandler.getRequest(RequestHandler.GET_ITAG_BY_ID, requestData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ITag tag = gson.fromJson(respond, new TypeToken<ITag>(){}.getType());

        return tag;
    }


    public static ArrayList<Comment>  getCommentsToItag(int iTagId){
        HashMap<String, String> requestData  = new HashMap<>();
        requestData.put("iTagId", String.valueOf(iTagId));

        String respond = null;
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            respond = RequestHandler.getRequest(RequestHandler.GET_ALL_COMMENT_BY_ITAG_ID, requestData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Comment> allComments = gson.fromJson(respond, new TypeToken<ArrayList<Comment>>(){}.getType());

        return allComments;
    }

    public static boolean updateItag(int iTagId , String content){
        HashMap<String, String> requestData  = new HashMap<>();
        requestData.put("iTagId", String.valueOf(iTagId));
        requestData.put("content", content);
        String respond = null;

        try {
            respond = RequestHandler.getRequest(RequestHandler.UPDATE_ITAG_INFO, requestData);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteItag(int iTagId){
        HashMap<String, String> requestData  = new HashMap<>();
        requestData.put("iTagId", String.valueOf(iTagId));

        String respond = null;

        try {
            respond = RequestHandler.getRequest(RequestHandler.CREATE_NEW_ITAG, requestData);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] arg) {
        ArrayList<ITag> itags = discoverItags(100,100,"100");
        System.out.print("cccc");
    }
}
