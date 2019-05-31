package com.example.hp.timeapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.hp.timeapp.R;
import com.example.hp.timeapp.entities.GetServiceById;
import com.example.hp.timeapp.entities.Organization;
import com.example.hp.timeapp.entities.SingleOrganization;
import com.example.hp.timeapp.ui.SingleActivity;
import com.example.hp.timeapp.util.Comman;
import com.facebook.stetho.common.ArrayListAccumulator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleItemAdapter extends RecyclerView.Adapter<SingleItemAdapter.MyViewHolder> {

    private final static String TAG = "SingleItemAdapter";

    private Context context;
    private List<SingleOrganization> postList;


    public SingleItemAdapter(Context context, List<SingleOrganization> postList) {
        this.context = context;
        this.postList = postList;
    }

//    private int Size(){
//
//        int list = new ArrayList(Arrays.asList(postList)).size();
//
//        return list;
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_layout, parent, false);

        return  new MyViewHolder(view);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private ImageView image;
        private TextView description;
        private TextView price;
        private TextView rating;
        private TextView address;
        private  TextView status;



        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            
            title = itemView.findViewById(R.id.text_1);
            description = itemView.findViewById(R.id.text_2);
            image = itemView.findViewById(R.id.image_1);
            price = itemView.findViewById(R.id.text_3);
//            title = itemView.findViewById(R.id.textView15);
            rating = itemView.findViewById(R.id.text_4);
            status = itemView.findViewById(R.id.text_5);

        }
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {



        holder.title.setText(postList.get(position).getName_of_organization());
//
        holder.description.setText(postList.get(position).getStatus_of_organization());
        holder.price.setText("Price: $" + postList.get(position).getPrice());
//        holder.title.setText(postList.getName_of_organization());
        holder.rating.setText(String.valueOf(postList.get(position).getRating())+ ".0");
        holder.status.setText(String.valueOf(postList.get(position).getStatus_of_organization()));

//        holder.timestamp.setText(recipe.getTimestamp());

        Log.w(TAG,"Image is COOL");
        Glide.with(context)
                .load(postList.get(position).getImage_main())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


}
