package cmu.sv.flubber.ihere.ws.remote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import cmu.sv.flubber.ihere.entities.Comment;
import cmu.sv.flubber.ihere.entities.ITag;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by zhengyiwang on 4/13/16.
 */
public class RemoteItag {
    /*
    This class is used to interact with server to get update itag data
     */
    public static void createItag(ITag iTag){}

    public static ArrayList<ITag> discoverItags(long longitude, long latitude, long direction) throws IOException {
        HashMap<String, String> requestData  = new HashMap<>();
        requestData.put("longitude", String.valueOf(longitude));
        requestData.put("latitude", String.valueOf(latitude));
        requestData.put("direction", String.valueOf(direction));

        final Gson gson = new Gson();

        String respond = RequestHandler.getRequest(RequestHandler.DISCOVER_ITAGS_AROUND, requestData);


        ArrayList<ITag> iTagList = gson.fromJson(respond, new TypeToken<ArrayList<ITag>>(){}.getType());

        return iTagList;
    }

    public static ArrayList<Comment>  getCommentsToItag(int itagId){
        return null;
    }

    public static void updateItag(ITag iTag){}

    public static void deleteItag(int iTagId){}

}
