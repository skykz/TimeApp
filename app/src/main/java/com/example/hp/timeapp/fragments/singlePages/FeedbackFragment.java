package com.example.hp.timeapp.fragments.singlePages;

import android.content.Context;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.hp.timeapp.R;
import com.example.hp.timeapp.TokenManager;
import com.example.hp.timeapp.adapters.ListFeedback;


import com.example.hp.timeapp.entities.FreeServicesResponse;
import com.example.hp.timeapp.entities.Organization;
import com.example.hp.timeapp.networkAPI.ApiService;
import com.example.hp.timeapp.networkAPI.RetrofitBuilder;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FeedbackFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ListFeedback singleAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Call<FreeServicesResponse> call;
    private List<Organization> lister;

    private ApiService apiService;


    private final String TAG = "FeedbackFragment";

    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_feedback,container,false);


        ///----- RecyclerView -----
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_feedback);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        apiService = RetrofitBuilder.createServiceWithAuth(ApiService.class);


        //loading feedback from server
        LoadData();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    public void LoadData() {

        call = apiService.freeServices();
        call.enqueue(new Callback<FreeServicesResponse>() {

            @Override
            public void onResponse(Call<FreeServicesResponse> call, Response<FreeServicesResponse> response) {

                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    lister = response.body().getData();
                    singleAdapter = new ListFeedback(context, lister);

                    singleAdapter.notifyDataSetChanged();

                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerView.setAdapter(singleAdapter);

//                    Toast.makeText(getContext(), "Все коментарии загружено", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FreeServicesResponse> call, Throwable t) {
                Toast.makeText(getContext(),"Ошибка " + t.getMessage(),Toast.LENGTH_SHORT).show();

//                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (context != null)
            context = null;
    }
}
