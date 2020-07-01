package com.divistant.konselorku.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.divistant.konselorku.R;
import com.google.gson.Gson;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class ReadNewsActivity extends AppCompatActivity {
    PostModel post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_news);
        Intent intent = getIntent();
        post = new Gson().fromJson(intent.getStringExtra("post"),PostModel.class);

        final ImageView image = findViewById(R.id.content_image);
        final TextView tv_category = findViewById(R.id.content_category);
        WebView webview = findViewById(R.id.content_text);

        final RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        Glide.with(image.getContext())
                .load(R.drawable.ic_launcher_foreground)
                .apply(requestOptions)
                .into(image);
        if(!ReadNewsActivity.this.isFinishing() && image.getContext() != null){
            post.GetImageUrl(new ImageGetter() {
                @Override
                public void imageGetter(String url) {
                    if (!ReadNewsActivity.this.isFinishing() && image.getContext() != null) {
                        Glide.with(image.getContext())
                                .load(url)
                                .apply(requestOptions)
                                .into(image);
                    }

                }
            });
        }

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
        TextView tv = findViewById(R.id.content_title);
        tv.setText(post.getTitle().getRendered());
    }
}
