package cmu.sv.flubber.ihere.ws.remote;

import java.util.ArrayList;

import cmu.sv.flubber.ihere.entities.Comment;
import cmu.sv.flubber.ihere.entities.ITag;

/**
 * Created by zhengyiwang on 4/13/16.
 */
public class RemoteItag {
    /*
    This class is used to interact with server to get update itag data
     */
    public static void createItag(ITag iTag){}

    public static ArrayList<ITag> discoverItags(long longitude, long latitude){
        return null;
    }

    public static ArrayList<Comment>  getCommentsToItag(int itagId){
        return null;
    }

    public static void updateItag(ITag iTag){}

    public static void deleteItag(int iTagId){}

}
