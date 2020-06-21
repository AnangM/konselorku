package com.divistant.konselorku.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.divistant.konselorku.BuildConfig;
import com.divistant.konselorku.R;
import com.divistant.konselorku.net.news.NewsApi;
import com.divistant.konselorku.net.news.NewsInterface;
import com.divistant.util.RecyclerItemClickListener;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment{
    protected  Activity mActivities;

    ViewPager viewPager;
    private final List<PostModel> postSlider = new ArrayList<>();

    List<Integer> color;
    List<String> colorName;

    private final List<PostModel> postList = new ArrayList<>();
    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        mActivities = activity;
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

    View view = inflater.inflate(R.layout.fragment_dashboard,container,false);
    final RecyclerView rv = view.findViewById(R.id.news_rv);
    final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
            LinearLayoutManager.VERTICAL,false);
    rv.setLayoutManager(layoutManager);
    final PostAdapter adapter = new PostAdapter(postList);
    rv.setAdapter(adapter);

    final ProgressBar barsatu = (ProgressBar) view.findViewById(R.id.dash_loading_one);
    final ProgressBar bardua = (ProgressBar) view.findViewById(R.id.dash_loading_two);
    barsatu.setVisibility(View.VISIBLE);
    bardua.setVisibility(View.VISIBLE);

    viewPager = (ViewPager) view.findViewById(R.id.view_pager);


    final SliderAdapter sa = new SliderAdapter(getActivity(),postSlider);
    viewPager.setAdapter(sa);



    final NewsInterface service = NewsApi.getClient().create(NewsInterface.class);
        Call<List<PostModel>> call= service.getLatestnews();
        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                bardua.setVisibility(View.GONE);
                barsatu.setVisibility(View.GONE);
                final List<PostModel> posts = response.body();
                if(posts.size() > 0){
                    for(int i =0; i< posts.size();i++){
                        if(i <=2){
                            postSlider.add(posts.get(i));
                        }
                        if(i > 2) {
                            postList.add(posts.get(i));
                        }
                    }
                }

                rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rv,
                        new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent i = new Intent(getActivity().getApplicationContext(),ReadNewsActivity.class);
                        i.putExtra("post",new Gson().toJson(posts.get(position)));
                        startActivity(i);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
                adapter.notifyDataSetChanged();
                sa.notifyDataSetChanged();
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new SliderTimer(),4000,6000);
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                Log.e("[API ERROR]",t.toString());
            }

        });




    return view;
    }

    private class SliderTimer extends TimerTask{
        @Override
        public void run() {
            mActivities.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem() < postSlider.size() -1 ){
                        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                    }else{
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

}






