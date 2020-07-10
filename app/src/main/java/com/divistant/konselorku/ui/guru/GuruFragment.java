package com.divistant.konselorku.ui.guru;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.divistant.konselorku.R;
import com.divistant.net.API;
import com.divistant.net.GuruInterface;
import com.divistant.util.GeneralResponse;
import com.divistant.util.RecyclerItemClickListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuruFragment extends Fragment {
    List<GuruModel> gurus = new ArrayList<>();
    SharedPreferences pref;
    final String TAG = "GURU FR";
    public GuruFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_guru,container,false);
        final TextView tv = view.findViewById(R.id.guru_ph);
        EditText cari = view.findViewById(R.id.cari_guru);

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final ProgressBar loading = view.findViewById(R.id.guru_loading);
        RecyclerView rv = view.findViewById(R.id.guru_rv);

        tv.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);

        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);
        final GuruAdapter adapter = new GuruAdapter(gurus);
        rv.setAdapter(adapter);

        GuruInterface service = API.getClient().create(GuruInterface.class);
        Call<GeneralResponse<GuruModel>> call = service.getGuru(pref.getString("TOKEN","def"));
        call.enqueue(new Callback<GeneralResponse<GuruModel>>() {
            @Override
            public void onResponse(Call<GeneralResponse<GuruModel>> call, Response<GeneralResponse<GuruModel>> response) {
                if(response.code() == 200){
                    GeneralResponse<GuruModel> resp = response.body();
                    if(resp.getListSize() < 1){
                        loading.setVisibility(View.GONE);
                        tv.setVisibility(View.VISIBLE);
                    }else{
                        for(GuruModel guru : resp.getData()){
                            gurus.add(guru);
                        }
                        loading.setVisibility(View.GONE);
                        tv.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    loading.setVisibility(View.GONE);
                    tv.setText(response.code() +"");
                    tv.setVisibility(View.VISIBLE);
                   if(response.body() != null){
                       Log.e(TAG, response.body().getMessage());
                       Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                   }
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse<GuruModel>> call, Throwable t) {
                loading.setVisibility(View.GONE);
                tv.setText(t.getMessage());
                tv.setVisibility(View.VISIBLE);
                
                if(!(GuruFragment.this.isDetached() || GuruFragment.this.isRemoving() || GuruFragment.this.getView() == null)){
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();
                }
                Log.e(TAG, t.getMessage());
            }
        });

        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rv,
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GuruModel guru = gurus.get(position);
                Intent intent = new Intent(getActivity(), GuruDetail.class);
                intent.putExtra("guru", new Gson().toJson(guru));
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        cari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }
}
