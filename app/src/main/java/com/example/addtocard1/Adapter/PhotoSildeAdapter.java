package com.example.addtocard1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.addtocard1.R;

import java.util.List;

public class PhotoSildeAdapter extends PagerAdapter {
    private Context mContext;
    private List<Photo> photoList;

    public PhotoSildeAdapter(Context mContext, List<Photo> photoList) {
        this.mContext = mContext;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.itemimgproduct_slide,container,false);
        ImageView imageView = view.findViewById(R.id.img_photo);
        Photo photo = photoList.get(position);
        if (photo!=null){
            Glide.with(mContext).load(photo.getResourceID()).into(imageView);
        }
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        if (photoList!=null){
            return photoList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
