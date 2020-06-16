package com.divistant.konselorku.ui.dashboard;

import android.util.Log;

import com.divistant.konselorku.net.news.NewsApi;
import com.divistant.konselorku.net.news.NewsInterface;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostModel  {
    @SerializedName("id") private String id;
    @SerializedName("date") private String date;
    @SerializedName("modified") private String mod;
    @SerializedName("status") private String status;
    @SerializedName("link") private  String link;
    @SerializedName("title") private TitleModel title;
    @SerializedName("content") private  ContentModel content;
    @SerializedName("featured_media") private  String featuredMedia;
    @SerializedName("categories") private List<String> category;
    private String imageUrl;


    public PostModel(String id, String date, String mod, String status, String link,
                     TitleModel title, ContentModel content, String featuredMedia, List<String> cat) {
        this.id = id;
        this.date = date;
        this.mod = mod;
        this.status = status;
        this.link = link;
        this.title = title;
        this.content = content;
        this.featuredMedia = featuredMedia;
        this.category = cat;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public String getFeaturedMedia() {
        return featuredMedia;
    }

    public void setFeaturedMedia(String featuredMedia) {
        this.featuredMedia = featuredMedia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public TitleModel getTitle() {
        return title;
    }

    public void setTitle(TitleModel title) {
        this.title = title;
    }

    public ContentModel getContent() {
        return content;
    }

    public void setContent(ContentModel content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public void CategoryGetter(final CategoryGetter categoryGetter) {

        final NewsInterface service = NewsApi.getClient().create(NewsInterface.class);
        Call<CategoryModel> call = service
                .getAdvancedCategory("https://konselorku.com/wp-json/wp/v2/categories/"
                        + this.category.get(0));
        call.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                CategoryModel cats = response.body();
                categoryGetter.CategoryGetter(cats.getName());
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                Log.e("[API ERRROR]","Cannot Retrieve Category Data" + t.getMessage() );
            }
        });
    }

    public void GetImageUrl(final ImageGetter imageGetter){
        final NewsInterface service = NewsApi.getClient().create(NewsInterface.class);
        Call<MediaModel> media = service.getMedia(NewsApi.baseUrl + "media/" + this.featuredMedia);
        media.enqueue(new Callback<MediaModel>() {
            @Override
            public void onResponse(Call<MediaModel> call, Response<MediaModel> response) {
                Log.e("API CIHUYT", response.raw().toString());
                MediaModel media = response.body();
                imageGetter.imageGetter(media.getMedia());
            }
            @Override
            public void onFailure(Call<MediaModel> call, Throwable t) {

            }
        });
    }
}
