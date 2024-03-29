package cmu.sv.flubber.ihere.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import cmu.sv.flubber.ihere.R;
import cmu.sv.flubber.ihere.dbLayout.DBLocalConnector;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected  void superOnCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        DBLocalConnector dbLocalConnector = new DBLocalConnector(HomeActivity.this);
        dbLocalConnector.init();

        //Show user name on menu header
        String name;

        Intent intent = getIntent();
        name  = intent.getStringExtra("UserName");

        //check local db if there are existing user
        if(name == null){
            name = dbLocalConnector.getUserName();
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            name = settings.getString("username", "");

            if(name.equals("")){
                Intent intent1 = new Intent(this,LoginActivity.class);
                startActivity(intent1);
            }
        }


        View head = navigationView.getHeaderView(0);

        TextView username = (TextView) head.findViewById(R.id.userNameInHeader);
        username.setText("Hello, " + name);





    }


    public void updateUserName(){

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    //TODO display name of the user

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle login view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_discover) {
            Intent intent = new Intent(this, DiscoveryActivity.class);
            startActivity(intent);


            // Handle the camera action
        } else if (id == R.id.nav_create) {
            Intent intent = new Intent(this, CreateActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("userid", 0);
            editor.putString("username", "");

            editor.commit();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
