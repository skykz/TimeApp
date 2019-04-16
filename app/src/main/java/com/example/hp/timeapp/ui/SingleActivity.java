package com.example.hp.timeapp.ui;


import android.content.Intent;

import android.os.Handler;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.View;


import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hp.timeapp.R;
import com.example.hp.timeapp.TokenManager;
import com.example.hp.timeapp.adapters.CustomSwipeAdapter;

import com.example.hp.timeapp.auth.LoginActivity;

import com.example.hp.timeapp.entities.GetServiceById;
import com.example.hp.timeapp.entities.SingleOrganization;
import com.example.hp.timeapp.networkAPI.ApiService;
import com.example.hp.timeapp.networkAPI.RetrofitBuilder;
import com.facebook.shimmer.ShimmerFrameLayout;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SingleActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private final String TAG = "SingleActivity";

    private ShimmerFrameLayout mShimmerViewContainer;
    private ApiService apiService;
    private TokenManager tokenManager;

    private Toolbar toolbar;
    private Call<SingleOrganization> call;

//    private RecyclerView recyclerView;
//    private SingleItemAdapter mAdapter;
    private CustomSwipeAdapter customSwipeAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String[] imageUrls = new String[]
            {
            "https://subshop.kz/images/organizations/slider1.jpg",
            "https://subshop.kz/images/organizations/slider2.jpg",
            "https://subshop.kz/images/organizations/slider3.jpg",
            "https://subshop.kz/images/organizations/slider1.jpg"
            };


    ViewPager viewPager;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_data);

//        mShimmerViewContainer = findViewById(R.id.shimmer_view_container1);

        toolbar = findViewById(R.id.toolbar_certain1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.loadingSingle);


        tokenManager = TokenManager.getInstance(getSharedPreferences("preferences",MODE_PRIVATE));

        if (tokenManager.getToken().getAccessToken() == null){
            startActivity(new Intent(SingleActivity.this, LoginActivity.class));
            finish();
        }

        apiService = RetrofitBuilder.createServiceWithAuth(ApiService.class,tokenManager);

//        recyclerView = findViewById(R.id.recycler_view1);

//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(llm);


        viewPager = (ViewPager) findViewById(R.id.viewPager_slider);


        getOrganizationById();
    }


    @Override
    public void onRefresh() {
        Toast.makeText(this, "Обновление...", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getOrganizationById();

            }
        }, 1300);
    }


    private int getIdOrganization(){
        int organization_id = 0;
        Bundle intent = getIntent().getExtras();

        if (intent != null){
            organization_id = intent.getInt("id_organization");
        }

        return  organization_id;
    }

    private void getOrganizationById() {

        ShowLoading();

        call = apiService.getServices(getIdOrganization());
        call.enqueue(new Callback<SingleOrganization>() {

            @Override
            public void onResponse(Call<SingleOrganization> call, Response<SingleOrganization> response) {

                if (response.isSuccessful()) {
                    SingleOrganization org = response.body();

                    Log.d(TAG, "onResponse: Single Images : " + org);

//                    mAdapter = new SingleItemAdapter(SingleActivity.this,org);

                    customSwipeAdapter = new CustomSwipeAdapter(SingleActivity.this, imageUrls);
                    viewPager.setAdapter(customSwipeAdapter);

                    TextView view = findViewById(R.id.rating_input);
                    view.setText(String.valueOf(org.getRating()));

                    TextView textView = findViewById(R.id.textView15);
                    textView.setText(org.getName_of_organization());

                    TextView textView1 = findViewById(R.id.textView20);
                    textView1.setText(org.getName_of_organization());

                    TextView textView2 = findViewById(R.id.textView21);
                    textView2.setText(org.getName_of_organization());

                    HideLoading();

                    Toast.makeText(getApplicationContext(), "Все успешно загружено", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Не могу загрузить данные" + tokenManager.getToken(), Toast.LENGTH_LONG).show();
                    tokenManager.deleteToken();
                    startActivity(new Intent(SingleActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<SingleOrganization> call, Throwable t) {

                Log.w(TAG,"onFailure: " + t.getMessage());
                Log.w(TAG,"AAAAAA: " + tokenManager.getToken());

                tokenManager.deleteToken();
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    protected void onResume() {

        super.onResume();
//        mShimmerViewContainer.startShimmer();

    }


    @Override
    protected void onStop() {
        super.onStop();
//        mShimmerViewContainer.stopShimmer();
    }

    private void ShowLoading(){

        progressBar.setVisibility(View.VISIBLE);

    }
    private void HideLoading()
    {
        progressBar.setVisibility(View.GONE);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null)
        {
            call.cancel();
            call = null;
        }
    }

}

