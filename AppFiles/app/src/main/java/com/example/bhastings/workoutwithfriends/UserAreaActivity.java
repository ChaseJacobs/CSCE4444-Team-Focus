package com.example.bhastings.workoutwithfriends;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserAreaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Bundle bundle = new Bundle();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();
        String username;
        username = intent.getStringExtra("username");

        bundle.putString("username", username);
        setContentView(R.layout.activity_user_area);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Workout With Friends");

        HomeFragment home = new HomeFragment();
        home.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_user_area, home);
        fragmentTransaction.commit();




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View v = navigationView.getHeaderView(0);
        TextView navMessage = (TextView) v.findViewById(R.id.navMessage);
        navMessage.setText("Logged in as:");
        TextView navUser = (TextView) v.findViewById(R.id.navUsername);
        navUser.setText(username);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       /* if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
       */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_area, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SettingsFragment settings = new SettingsFragment();
            settings.setArguments(bundle);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_user_area, settings).commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_home) {
            HomeFragment homeFragment = new HomeFragment();
            homeFragment.setArguments(bundle);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_user_area, homeFragment).commit();
        } else if (id == R.id.nav_view_profile) {
            ProfileFragment profileFragment = new ProfileFragment();
            profileFragment.setArguments(bundle);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_user_area, profileFragment).commit();

        } else if (id == R.id.nav_friends_list) {
            FriendsListFragment friendsFragment = new FriendsListFragment();
            friendsFragment.setArguments(bundle);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_user_area, friendsFragment).commit();

        } else if (id == R.id.nav_activities) {
            ActivitiesFragment activitiesFragment = new ActivitiesFragment();
            activitiesFragment.setArguments(bundle);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_user_area, activitiesFragment).commit();

        } else if (id == R.id.nav_add_workout) {
            AddWorkoutFragment addWorkoutFragment = new AddWorkoutFragment();
            addWorkoutFragment.setArguments(bundle);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_user_area, addWorkoutFragment).commit();

        } else if (id == R.id.nav_diet) {
            DietViewFragment dietFragment = new DietViewFragment();
            dietFragment.setArguments(bundle);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_user_area, dietFragment).commit();

        } else if (id == R.id.nav_statistics) {
            StatisticsFragment statisticsFragment = new StatisticsFragment();
            statisticsFragment.setArguments(bundle);
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_user_area, statisticsFragment).commit();

        } else if (id == R.id.nav_logout) {
            //go to logout Fragment
            LogoutFragment logoutFragment = new LogoutFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_user_area, logoutFragment).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
