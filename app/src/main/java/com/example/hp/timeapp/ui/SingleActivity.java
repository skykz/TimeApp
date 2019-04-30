package com.example.hp.timeapp.ui;


import android.content.Intent;

import android.os.Build;
import android.os.Handler;

import android.os.SystemClock;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.transition.TransitionManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.View;


import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hp.timeapp.R;
import com.example.hp.timeapp.TokenManager;
import com.example.hp.timeapp.adapters.CustomSwipeAdapter;

import com.example.hp.timeapp.adapters.PageAdapter;
import com.example.hp.timeapp.adapters.single_adapter.SingleAdapter;
import com.example.hp.timeapp.auth.LoginActivity;

import com.example.hp.timeapp.auth.NumberActivity;
import com.example.hp.timeapp.entities.GetServiceById;
import com.example.hp.timeapp.entities.SingleOrganization;
import com.example.hp.timeapp.networkAPI.ApiService;
import com.example.hp.timeapp.networkAPI.RetrofitBuilder;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SingleActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private final String TAG = "SingleActivity";

    private ApiService apiService;
    private TokenManager tokenManager;

    private Toolbar toolbar;
    private Call<SingleOrganization> call;

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
//    ProgressBar progressBar;
    LinearLayout bottomSheet;

    @BindView(R.id.single_container)
    LinearLayout container;

    @BindView(R.id.loadingSingle)
    ProgressBar loader;

    @BindView(R.id.data_single)
    LinearLayout formContainer;

    BottomSheetBehavior bottomSheetBehavior;


    private TabLayout tabLayout;
    private TabItem tabActual;
    private TabItem tabAll;
    private SingleAdapter pageAdapter;

    private FirebaseAuth fbAuth;
    private FirebaseUser user;

    @BindView(R.id.button_order_accept)
    Button buttonOrder;

    private long lastClickTime = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_data);


        toolbar = findViewById(R.id.toolbar1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fbAuth = FirebaseAuth.getInstance();
        user = fbAuth.getCurrentUser();


        if (user.getUid() == null){
            startActivity(new Intent(SingleActivity.this, NumberActivity.class));
            finish();
        }


        apiService = RetrofitBuilder.createServiceWithAuth(ApiService.class);

        tabLayout = findViewById(R.id.tablayout_single);
        tabActual = findViewById(R.id.tab_info);
        tabAll = findViewById(R.id.tab_feedback);

        viewPager = findViewById(R.id.viewSingle);

        pageAdapter = new SingleAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
               @Override
               public void onTabSelected(TabLayout.Tab tab) {
                   viewPager.setCurrentItem(tab.getPosition());

                   if (tab.getPosition() == 1) {

                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                           getWindow().setStatusBarColor(ContextCompat.getColor(SingleActivity.this, R.color.fragment1));

                       }
                   } else {

                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                           getWindow().setStatusBarColor(ContextCompat.getColor(SingleActivity.this, R.color.colorPrimaryDark));

                       }
                   }
               }

               @Override
               public void onTabUnselected(TabLayout.Tab tab) {

               }

               @Override
               public void onTabReselected(TabLayout.Tab tab) {

               }

           });

        bottomSheet = (LinearLayout)findViewById(R.id.bottom_sheet_layout);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);



        Toast.makeText(getApplicationContext(),"Current user " + fbAuth.getCurrentUser(),Toast.LENGTH_SHORT).show();

        //getting single service data from server
        getOrganizationById();

        Button buttonOrder = (Button) findViewById(R.id.button_order_accept);
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });

    }

    void test(){
        // preventing double, using threshold of 1000 ms
        if (SystemClock.elapsedRealtime() - lastClickTime < 1500){
            return;
        }
        lastClickTime = SystemClock.elapsedRealtime();

        Intent intent = new Intent(this,WaitingActivity.class);
        startActivity(intent);
        finish();

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

//        ShowLoading();

        call = apiService.getServices(getIdOrganization());
        call.enqueue(new Callback<SingleOrganization>() {

            @Override
            public void onResponse(Call<SingleOrganization> call, Response<SingleOrganization> response) {

                if (response.isSuccessful()) {
                    SingleOrganization org = response.body();

                    Log.d(TAG, "onResponse: Single Images : " + org);

//                    mAdapter = new SingleItemAdapter(SingleActivity.this,org);

                    customSwipeAdapter = new CustomSwipeAdapter(SingleActivity.this, imageUrls);
//                    viewPager1.setAdapter(customSwipeAdapter);

                    TextView view = findViewById(R.id.rating_input);
                    view.setText(String.valueOf(org.getRating()));

                    TextView textView = findViewById(R.id.textView15);
                    textView.setText(org.getName_of_organization());

                    TextView textView1 = findViewById(R.id.textView20);
                    textView1.setText(org.getName_of_organization());

                    TextView textView2 = findViewById(R.id.textView21);
                    textView2.setText(org.getName_of_organization());


//                    HideLoading();

                    Toast.makeText(getApplicationContext(), "Все успешно загружено", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SingleOrganization> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Ошибка " + t.getMessage(),Toast.LENGTH_SHORT).show();

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

//    private void ShowLoading(){
//
//        TransitionManager.beginDelayedTransition(container);
//        formContainer.setVisibility(View.GONE);
//        loader.setVisibility(View.VISIBLE);
//
//    }
//    private void HideLoading()
//    {
//
//        TransitionManager.beginDelayedTransition(container);
//        formContainer.setVisibility(View.VISIBLE);
//        loader.setVisibility(View.GONE);
//
//    }
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

