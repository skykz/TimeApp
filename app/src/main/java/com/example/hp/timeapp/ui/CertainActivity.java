package com.example.hp.timeapp.ui;


import android.content.Intent;

import android.os.Handler;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hp.timeapp.R;
import com.example.hp.timeapp.TokenManager;
import com.example.hp.timeapp.adapters.PostListAdapter;

import com.example.hp.timeapp.auth.LoginActivity;
import com.example.hp.timeapp.entities.FreeServicesResponse;
import com.example.hp.timeapp.entities.Organization;
import com.example.hp.timeapp.fragments.MapFragment;
import com.example.hp.timeapp.googleMaps.MapActivity;
import com.example.hp.timeapp.networkAPI.ApiService;
import com.example.hp.timeapp.networkAPI.RetrofitBuilder;
import com.facebook.shimmer.ShimmerFrameLayout;


import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CertainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,PostListAdapter.OnNoteListener{

    private final String TAG = "CertainActivity";


    private Toolbar toolbar;
    private ImageButton mapButton;
    private ShimmerFrameLayout mShimmerViewContainer;
    private RecyclerView recyclerView;
    private PostListAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ApiService apiService;
    private TokenManager tokenManager;
    private Call<FreeServicesResponse> call;
    private List<Organization> lister;


    private PostListAdapter.OnNoteListener onNoteListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certain);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);

            tokenManager = TokenManager.getInstance(getSharedPreferences("preferences", MODE_PRIVATE));

            if (tokenManager.getToken() == null) {
                startActivity(new Intent(CertainActivity.this, LoginActivity.class));
                finish();
            }

            apiService = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);

            recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);

            toolbar = findViewById(R.id.toolbar_certain);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            getListOfOrganization();

            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
            swipeRefreshLayout.setOnRefreshListener(this);

            init();


    }


    //refresh the current fragment
    @Override
    public void onRefresh() {

        Toast.makeText(this, "Обновление...", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                getListOfOrganization();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1300);
    }

    //google map initializing
    private void init(){
        mapButton = (ImageButton)findViewById(R.id.map_start);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMap = new Intent(CertainActivity.this, MapActivity.class);
                startActivity(intentMap);
            }
        });
    }

    //getting full data item
    private void getListOfOrganization(){


        call = apiService.freeServices();
        call.enqueue(new Callback<FreeServicesResponse>() {

            @Override
            public void onResponse(Call<FreeServicesResponse> call, Response<FreeServicesResponse> response) {

                Log.w(TAG,"onResponse: " + response);
                Log.d(TAG,"onTokenManager: -------------" + tokenManager.getToken().toString());

                if (response.isSuccessful())
                {
                    lister = response.body().getData();

                    mAdapter = new PostListAdapter(lister, CertainActivity.this, new PostListAdapter.OnNoteListener() {
                        @Override
                        public void onNoteClick(int position) {
                            int postId = lister.get(position).getId();


                            Intent intent = new Intent(CertainActivity.this,SingleActivity.class);
                            intent.putExtra("id_organization",postId);

                            startActivity(intent);
                        }
                    });

                    mAdapter.notifyDataSetChanged();
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);

                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(mAdapter);


                    Toast.makeText(getApplicationContext(),"Все успешно загружено",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getApplicationContext(),"Не могу загрузить данные" + tokenManager.getToken(),Toast.LENGTH_LONG).show();
                        tokenManager.deleteToken();
                        startActivity(new Intent(CertainActivity.this, LoginActivity.class));
                        finish();
                }
            }

            @Override
            public void onFailure(Call<FreeServicesResponse> call, Throwable t) {
                Log.w(TAG,"onFailure: " + t.getMessage());
                Log.w(TAG,"AAAAAA: " + tokenManager.getToken());

                tokenManager.deleteToken();
//                swipeRefreshLayout.setRefreshing(false);
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
        mShimmerViewContainer.startShimmer();
    }


    @Override
    protected void onStop() {
        super.onStop();
        mShimmerViewContainer.stopShimmer();
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

    @Override
    public void onNoteClick(int position) { }
}
