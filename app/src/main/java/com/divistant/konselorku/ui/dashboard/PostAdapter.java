package com.divistant.konselorku.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.divistant.konselorku.R;
import com.divistant.konselorku.net.news.NewsApi;
import com.divistant.konselorku.net.news.NewsInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<PostModel> newsList;
    public ArticleClickListener articleListener;
    private final List<CategoryModel> categories = new ArrayList<>();

    public PostAdapter(List<PostModel> postList) {
        this.newsList = postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,
                parent,false);
        return  new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final PostModel news = newsList.get(position);
        if(!emptyS(news.getTitle().getRendered())){
            holder.headLine.setText(news.getTitle().getRendered());
        }
        holder.date.setText(formatDate(news.getDate()));

        news.CategoryGetter(new CategoryGetter() {
            @Override
            public void CategoryGetter(String category) {
                holder.category.setText(category.toUpperCase());
            }
        });

        final RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        if(!((Activity)holder.imageView.getContext()).isFinishing()){
        Glide.with(holder.imageView.getContext())
                .load(R.drawable.ic_launcher_foreground)
                .apply(requestOptions)
                .into(holder.imageView);

        news.GetImageUrl(new ImageGetter() {
            @Override
            public void imageGetter(String url) {
                Glide.with(holder.imageView.getContext())
                        .load(url)
                        .apply(requestOptions)
                        .into(holder.imageView);
            }
        });
        }
        holder.parent.setTag(news);

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    private String getCategoryName(String CategoryId){
        String tag = null;
        for(int i =0; i < categories.size(); i++){
            if(CategoryId.equals(categories.get(i).getId())){
                tag = categories.get(i).getName();
                break;
            }
        }
        return  tag;
    }

    private String formatDate (String date){
        String formattedDate = date;
        formattedDate = formattedDate.split("T")[0];
        SimpleDateFormat before = new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("dd MMM YYYY");
        try {
            Date beforeDate = before.parse(formattedDate);
            formattedDate = format.format(beforeDate);
        }catch (Exception e){
            Log.e("[Method Error]","Failed to parse date exception!");
        }
        return formattedDate;
    }

    private boolean emptyS(String s){
        return  TextUtils.isEmpty(s);
    }

    class ViewHolder extends  RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView headLine;
        private TextView category;
        private TextView date;
        private LinearLayout parent;

        public  ViewHolder(@NonNull View item){
            super(item);
            parent = item.findViewById(R.id.news_item_parent);
            imageView = item.findViewById(R.id.news_item_image);
            headLine = item.findViewById(R.id.news_item_headline);
            category = item.findViewById(R.id.news_item_category);
            date = item.findViewById(R.id.news_item_date);

            parent.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(articleListener != null){
                        articleListener.onArticleClick(getAdapterPosition(), v);
                    }else{
                        Log.e("[Article Listener]","Null article listener");
                    }

                }
            });

        }

    }
    public void setOnArticleClickListener(ArticleClickListener listener){
        this.articleListener = listener;
    }
}
