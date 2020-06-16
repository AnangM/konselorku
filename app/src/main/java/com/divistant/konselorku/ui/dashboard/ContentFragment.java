package com.divistant.konselorku.ui.dashboard;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.divistant.konselorku.R;


public class ContentFragment extends Fragment {
    protected  Activity mActivity;
    private PostModel post;


    public ContentFragment() {
    }

    public ContentFragment(PostModel post) {
        this.post = post;
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        Bundle bundle = getArguments();
////        this.post = (PostModel) bundle.getSerializable("post");
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        final ImageView image = view.findViewById(R.id.content_image);
        final TextView tv_category = view.findViewById(R.id.content_category);
        WebView webview = view.findViewById(R.id.content_text);

        final RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        Glide.with(image.getContext())
                .load(R.drawable.ic_launcher_foreground)
                .apply(requestOptions)
                .into(image);

        post.GetImageUrl(new ImageGetter() {
            @Override
            public void imageGetter(String url) {
                Glide.with(image.getContext())
                        .load(url)
                        .apply(requestOptions)
                        .into(image);
            }
        });

        post.CategoryGetter(new CategoryGetter() {
            @Override
            public void CategoryGetter(String category) {
                tv_category.setText(category.toUpperCase());
            }
        });

        WebSettings webSettings = webview.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");
        webview.setBackgroundColor(Color.parseColor("#F2F2FF"));
        webview.loadDataWithBaseURL(null, post.getContent().getRendered(),
                "text/html","utf-8",null);
        webview.setWebViewClient(new WebViewClient());
        TextView tv = view.findViewById(R.id.content_title);
        tv.setText(post.getTitle().getRendered());



        return view;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
}
