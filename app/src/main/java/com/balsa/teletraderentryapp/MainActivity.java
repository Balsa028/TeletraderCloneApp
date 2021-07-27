package com.balsa.teletraderentryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.balsa.teletraderentryapp.Fragments.MainFragment;
import com.balsa.teletraderentryapp.Fragments.MyInfoFragment;
import com.balsa.teletraderentryapp.Fragments.NewsFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //funcija za inicijalizaciju view-a
        initViews();

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.light_blue));
        //kreiranje drawerToggla
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_closed, R.string.drawer_opened);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.toggle_color));

        initNavigationView();

        //inflating frameLayout with main fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new MainFragment())
                .addToBackStack(null)
                .commit();
    }

    private void initNavigationView() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
                // TODO: 23.7.21. Finish here
                switch (item.getItemId()) {
                    case R.id.myCV:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainer, new MyInfoFragment())
                                .commit();
                        drawer.closeDrawers();
                        break;
                    case R.id.myGitHub:
                        Intent intent = new Intent(MainActivity.this, GitHubActivity.class);
                        startActivity(intent);
                        drawer.closeDrawers();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }


    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    private void initViews() {
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

    }
}