package cmu.sv.flubber.ihere.ui;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cmu.sv.flubber.ihere.entities.Comment;
import cmu.sv.flubber.ihere.entities.ITag;
import cmu.sv.flubber.ihere.ws.remote.RemoteItag;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class HistoryContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<ITag> ITEMS = new ArrayList<ITag>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, ITag> ITEM_MAP = new HashMap<String, ITag>();

    public static void addItem(ITag item) {
        ITEMS.add(item);
        ITEM_MAP.put(String.valueOf(item.getiTagId()), item);
    }

    public static void clear() {
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    private static ITag createDummyItem(int position) {
        ITag tag = new ITag(1,"Content", 12, Calendar.getInstance().getTime(), 21, new ArrayList<Comment>(), "");
        tag.setiTagId(position);
        return tag;
    }
}
