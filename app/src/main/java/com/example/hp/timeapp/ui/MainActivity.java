package com.example.hp.timeapp.ui;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Toast;


import com.example.hp.timeapp.R;
import com.example.hp.timeapp.TokenManager;
import com.example.hp.timeapp.adapters.PageAdapter;
import com.example.hp.timeapp.auth.LoginActivity;


import com.example.hp.timeapp.auth.NumberActivity;
import com.example.hp.timeapp.entities.FreeServicesResponse;
import com.example.hp.timeapp.networkAPI.ApiService;
import com.example.hp.timeapp.settings.SettingsActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;

import static com.example.hp.timeapp.util.Constants.USER_DATA;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = "MainActivity";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private TabItem tabActual;
    private TabItem tabAll;
    private ViewPager viewPager;
    private PageAdapter pageAdapter;
    private DrawerLayout drawer;
    protected ActionBarDrawerToggle toggle;

    private NavigationView navigationView;
//    private SharedPreferences sPref;
    public MenuItem menuItem;
    private Intent intent;
    private MaterialSearchView materialSearchView;

//    private ApiService apiService;
//    private TokenManager tokenManager;
//    private Call<FreeServicesResponse> call;

    SharedPreferences sharedPreferences;
    private FirebaseAuth fbAuth;

    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setLogo(R.drawable.time_white);
        }

        //material search views
        materialSearchView = findViewById(R.id.search_view);
        materialSearchView.setVoiceSearch(false);
        //        materialSearchView.setEnabled(true);

        //        tablayouts and items
         tabLayout = findViewById(R.id.tablayout);
         tabActual = findViewById(R.id.actual_tab);
//         tabAll = findViewById(R.id.all_tab);

         viewPager = findViewById(R.id.viewPager);

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

//                if (tab.getPosition() == 1) {
//                    toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.fragment1));
//                    tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.fragment1));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.fragment1_m));
//                    }
//                }   {
                if (tab.getPosition() == 0){ toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);

        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        fbAuth = FirebaseAuth.getInstance();

        sharedPreferences  = getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);


        Toast.makeText(getBaseContext(),"User name is " + sharedPreferences.getString("first_name",null),Toast.LENGTH_SHORT).show();

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
        getMenuInflater().inflate(R.menu.main, menu);

        menuItem = menu.findItem(R.id.action_search_view);
        materialSearchView.setMenuItem(menuItem);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history) {

            // Handle the camera action
        } else
             if (id == R.id.nav_news)
             {

             }
             else if (id == R.id.nav_help) {

            Log.i("History", "FRAGMENT");
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HistoryFragment()).commit();

        } else if (id == R.id.nav_settings) {
            Settings();

        } else if (id == R.id.profile) {

                 Intent intent = new Intent(this,ProfileActivity.class);
                 startActivity(intent);
             }
             else if (id == R.id.logout_menu){
                 logout();
             }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    void removeData(){
        SharedPreferences.Editor sPref = sharedPreferences.edit();

        sPref.remove("user_uid");
        sPref.remove("user_phone_number");
        fbAuth.signOut();
    }

    void getData(){

        sharedPreferences = getSharedPreferences(USER_DATA,MODE_PRIVATE);
        if (sharedPreferences.contains("user_uid")){

            String user_uid = sharedPreferences.getString("user_uid",null);
        }
        if (sharedPreferences.contains("user_phone_number")){
            String user_phone_number = sharedPreferences.getString("user_phone_number",null);
        }
    }


    public void logout(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Вы действительно хотите выйти?")
                .setContentText("Вы можете авторизоваться в любое время!")
                .setConfirmText("Да,выйти")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
//                        sDialog.dismissWithAnimation();
                        action_logout();
                    }
                })
                .setCancelButton("Отмена", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    public void action_logout(){
        removeData();

        intent = new Intent(this, NumberActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"Вы вышли!",Toast.LENGTH_SHORT).show();
        finish();
    }

    public void Settings(){
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        if (pDialog != null)
//            pDialog =null;
    }
}
