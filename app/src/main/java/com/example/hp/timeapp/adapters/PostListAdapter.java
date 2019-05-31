package com.example.hp.timeapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.hp.timeapp.R;
import com.example.hp.timeapp.entities.Organization;
import com.example.hp.timeapp.ui.CertainActivity;
import com.example.hp.timeapp.ui.SingleActivity;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.MyViewHolder>{

    private final static String TAG = "PostListAdapter";

    private Context context;
    private List<Organization> postList;
    private String [] images;
    private OnNoteListener MonNoteListener;
    private long lastClickTime = 0;


    public PostListAdapter(List<Organization> postList,String[] images,Context context,OnNoteListener onNoteListener) {

        this.postList = postList;
        this.context = context;
        this.MonNoteListener = onNoteListener;
        this.images = images;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView ID;
        public TextView title;
        private ImageView image;
        private ImageView image1;
        private TextView description;
        private TextView price;
        private TextView rating;
        private TextView used_amount;
        private LikeButton likeButton;

        OnNoteListener MonNoteListener;

        public MyViewHolder(@NonNull View itemView,OnNoteListener onNoteListener) {
            super(itemView);

            title = itemView.findViewById(R.id.textView5);
            description = itemView.findViewById(R.id.textView7);
            image = itemView.findViewById(R.id.imageView3);
            image1 = itemView.findViewById(R.id.imageView11);
            rating = itemView.findViewById(R.id.rating);
            price = itemView.findViewById(R.id.textView6);

//            ID = itemView.findViewById(R.id.id);
            used_amount = itemView.findViewById(R.id.txt_used_amount);

            likeButton = itemView.findViewById(R.id.like_button);

            this.MonNoteListener = onNoteListener;


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position  = getAdapterPosition();

            // preventing double сlick, using threshold of 1000 ms
            if (SystemClock.elapsedRealtime() - lastClickTime < 1500){
                return;
            }
            lastClickTime = SystemClock.elapsedRealtime();

            MonNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(itemView,MonNoteListener);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Organization posts = postList.get(position);

        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                holder.likeButton.setLiked(true);

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                holder.likeButton.setLiked(false);

            }
        });
//        holder.likeButton.setLiked(false);

//        holder.ID.getId();
        holder.title.setText(posts.getTitle());
        holder.description.setText(posts.getBody());
        holder.price.setText("Price: $" + posts.getPrice());
        holder.used_amount.setText(String.valueOf(posts.getUsed_by_people()));


        holder.rating.setText(String.valueOf(posts.getRating()+",0"));
        //holder.timestamp.setText(recipe.getTimestamp());

        Glide.with(context)//little changing with glide loader
                .load(postList.get(position).getImage_url())
                .into(holder.image);


            Glide.with(context)
                    .load(images[position])
                    .into(holder.image1);

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}
