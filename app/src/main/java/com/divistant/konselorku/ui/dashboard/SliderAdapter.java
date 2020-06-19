package com.divistant.konselorku.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.divistant.konselorku.R;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends PagerAdapter {
    private Context context;
    private List<PostModel> posts;
    private List<String> images = new ArrayList<>();

    public SliderAdapter(Context context, List<PostModel> posts) {
        this.context = context;
        this.posts = posts;
        this.images = images;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PostModel post = posts.get(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_slider,null);
        final TextView tv = (TextView) view.findViewById(R.id.slider_text);
        final ImageView iv = (ImageView) view.findViewById(R.id.slider_image);

        tv.setText(post.getTitle().getRendered());

        final RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        if(images.size() < position + 1 || images==null){
            post.GetImageUrl(new ImageGetter() {
            @Override
            public void imageGetter(String url) {
                if(!((Activity)iv.getContext()).isFinishing() && iv.getContext() != null){
                Glide.with(iv.getContext())
                        .load(url)
                        .apply(requestOptions)
                        .into(iv);
                images.add(url);
                }
            }
        });
        }else{
            if(!((Activity)iv.getContext()).isFinishing() && iv.getContext() != null) {
                Glide.with(iv.getContext())
                        .load(images.get(position))
                        .apply(requestOptions)
                        .into(iv);
            }
        }

        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager) container;
        View v = (View) object;
        vp.removeView(v);
    }
}
