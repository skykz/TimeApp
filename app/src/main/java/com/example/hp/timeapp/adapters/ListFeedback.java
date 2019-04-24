package com.example.hp.timeapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.timeapp.R;
import com.example.hp.timeapp.entities.Organization;

import java.util.List;

public class ListFeedback extends RecyclerView.Adapter<ListFeedback.MyViewHolder> {

    private Context context;
    List<Organization> postList;

    public ListFeedback(Context context,List<Organization> postList){
        this.context = context;
        this.postList = postList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;
        private ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textView5);
            description = itemView.findViewById(R.id.textView6);
            image = itemView.findViewById(R.id.imageView3);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_feedback, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Organization feedbacks = postList.get(i);
        myViewHolder.title.setText(feedbacks.getTitle());
        myViewHolder.description.setText(feedbacks.getBody());

//        Glide.with(context).load(R.drawable.ic_user).into(myViewHolder.image);

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }
}
