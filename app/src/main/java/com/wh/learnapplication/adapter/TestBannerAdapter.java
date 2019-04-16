package com.wh.learnapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

public class TestBannerAdapter extends StaticPagerAdapter {
    private String[] imgs;
    private Context context;

    public TestBannerAdapter(String[] imgs, Context context) {
        this.imgs = imgs;
        this.context = context;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        Glide.with(context).load(imgs[position]).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        return imageView;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }
}
