package cmu.sv.flubber.ihere.ui;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import cmu.sv.flubber.ihere.R;
import cmu.sv.flubber.ihere.entities.ITag;
import cmu.sv.flubber.ihere.ws.remote.RemoteItag;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link HistoryActivity}
 * in two-pane mode (on tablets) or a {@link ItagDetailActivity}
 * on handsets.
 */
public class ItagDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private ITag mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItagDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            int itagid = Integer.parseInt(getArguments().getString(ARG_ITEM_ID));

            try {
                mItem = new DetailTask().execute(String.valueOf(itagid)).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getContent());
            }
        }
    }

    private class DetailTask extends AsyncTask<String, Integer, ITag> {
        protected ITag doInBackground(String... strings) {
            return RemoteItag.getITagById(Integer.parseInt(strings[0]));
        }

        protected void onPostExecute(ITag res) {
            mItem = res;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.detail_content)).setText(mItem.getContent().toString());
            String loc = "Longitude: " + mItem.getLongitude() + ", Latitude: " + mItem.getLatitude();
            ((TextView) rootView.findViewById(R.id.detail_location)).setText(loc);

            // Date on the top
            if (mItem.getDate() != null)
                ((TextView) rootView.findViewById(R.id.detail_top)).setText(mItem.getDate().toString());
        }

        return rootView;
    }
}
