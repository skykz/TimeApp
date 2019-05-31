package com.example.hp.timeapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.timeapp.R;
import com.example.hp.timeapp.entities.SingleOrganization;
import com.example.hp.timeapp.models.ImagesById;

import java.util.List;

import static com.example.hp.timeapp.R.drawable.ic_location;

public class CustomSwipeAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private String[] list;

    public CustomSwipeAdapter(Context context, String [] list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o)
    {
        return (view == (LinearLayout)o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = layoutInflater.inflate(R.layout.swipe_layout,container,false);

        ImageView imageView = (ImageView)item.findViewById(R.id.image_slide);


//        imageView.setImageResource(imagesByIdList.get(position));
        Glide.with(context)
                .load(list[position])
                .thumbnail(0.1f)
                .into(imageView);

        container.addView(item);

        return item;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout) object);
    }
}
