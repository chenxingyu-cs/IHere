package cmu.sv.flubber.ihere.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cmu.sv.flubber.ihere.R;
import cmu.sv.flubber.ihere.adapter.CommentListAdapter;
import cmu.sv.flubber.ihere.entities.Comment;
import cmu.sv.flubber.ihere.entities.ITag;
import cmu.sv.flubber.ihere.ws.remote.RemoteComment;
import cmu.sv.flubber.ihere.ws.remote.RemoteItag;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link HistoryActivity}.
 */
public class ItagDetailActivity extends HomeActivity {

    private ITag mItem;
    private String m_Text = "";
    View recyclerView;
    CommentListAdapter clAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        //        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        //toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       onFloatClick(view);
                                   }
                               }
        );

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        int itagid = getIntent().getExtras().getInt(ItagDetailFragment.ARG_ITEM_ID);
        try {
            mItem = new DetailTask().execute(String.valueOf(itagid)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        if (mItem.getComments() == null) {
            mItem.setComments(new ArrayList<Comment>());
        }
        clAdaptor = new CommentListAdapter(mItem.getComments());
        recyclerView.setAdapter(clAdaptor);
    }

    private class DetailTask extends AsyncTask<String, Integer, ITag> {
        protected ITag doInBackground(String... strings) {
            return RemoteItag.getITagById(Integer.parseInt(strings[0]));
        }

        protected void onPostExecute(ITag res) {
            mItem = res;
            if (mItem != null) {
                ((TextView) findViewById(R.id.detail_content)).setText(mItem.getContent().toString());
                String loc = "Longitude: " + mItem.getLongitude() + ", Latitude: " + mItem.getLatitude();
                ((TextView) findViewById(R.id.detail_location)).setText(loc);

                // Date on the top
                if (mItem.getDate() != null)
                    ((TextView) findViewById(R.id.detail_time)).setText(mItem.getDate().toString()+ ",\t");
            }
        }
    }


    private void onFloatClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Comment");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO: add comment here
                m_Text = input.getText().toString();
                try {
                    new CommentTask().execute(m_Text).get();
                    mItem = new DetailTask().execute(String.valueOf(mItem.getiTagId())).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                clAdaptor.setmValues(mItem.getComments());
                clAdaptor.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private class CommentTask extends AsyncTask<String, Integer, Boolean> {
        protected Boolean doInBackground(String... strings) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            int userid = settings.getInt("userid", -1);
            Calendar calander = Calendar.getInstance();
            Date date = calander.getTime();
            Comment c = new Comment(mItem.getiTagId(), userid, strings[0], date);
            return RemoteComment.addComment(c);
        }

        protected void onPostExecute(Boolean res) {
            if (res) {
                Toast.makeText(getBaseContext(), "Comment success!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getBaseContext(), "Comment Failed!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        System.out.println("============on back pressed");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, HistoryActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
