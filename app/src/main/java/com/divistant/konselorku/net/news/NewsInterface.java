package com.divistant.konselorku.net.news;

import com.divistant.konselorku.ui.dashboard.CategoryModel;
import com.divistant.konselorku.ui.dashboard.MediaModel;
import com.divistant.konselorku.ui.dashboard.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewsInterface {
    @GET("posts")
    Call<List<PostModel>> getLatestnews();
    @GET
    Call<MediaModel> getMedia(@Url String url);
    @GET("categories")
    Call<List<CategoryModel>> getCategories();
    @GET
    Call<CategoryModel> getAdvancedCategory(@Url String url);

}
