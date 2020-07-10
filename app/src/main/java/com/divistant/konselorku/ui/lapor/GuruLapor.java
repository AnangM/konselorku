package com.divistant.konselorku.ui.lapor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.Toast;

import com.divistant.konselorku.R;

import com.divistant.net.API;
import com.divistant.net.LaporanInterface;
import com.divistant.util.GeneralResponse;
import com.divistant.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuruLapor extends Fragment {
    List<LaporanModel> laporans = new ArrayList<>();
    SharedPreferences pref;
    ProgressBar load;
    private final String TAG = "GURU LAPORAN";
    public GuruLapor() {
        // Required empty public constructor
    }

    public static GuruLapor newInstance(String param1, String param2) {
        GuruLapor fragment = new GuruLapor();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guru_lapor, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.guru_lapor_rv);
        pref = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()).getApplicationContext());
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);
        final LaporanAdapter laporanAdapter = new LaporanAdapter(laporans);
        rv.setAdapter(laporanAdapter);

        load = view.findViewById(R.id.guru_lapor_load);

        LaporanInterface laporanInterface = API.getClient().create(LaporanInterface.class);
        Call<GeneralResponse<LaporanModel>> call = laporanInterface.get(pref.getString("TOKEN","Def"));
        call.enqueue(new Callback<GeneralResponse<LaporanModel>>() {
            @Override
            public void onResponse(Call<GeneralResponse<LaporanModel>> call, Response<GeneralResponse<LaporanModel>> response) {
                if(response.code() == 200){
                    load.setVisibility(View.GONE);
                    GeneralResponse resp = response.body();
                    List<LaporanModel> laporan = resp.getData();
                    laporans.addAll(laporan);
                    laporanAdapter.notifyDataSetChanged();
                }else{
                    load.setVisibility(View.GONE);
                    GeneralResponse resp = response.body();
                    String m = String.valueOf(response.code() );
                    Toast.makeText(getActivity(), m,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse<LaporanModel>> call, Throwable t) {
                load.setVisibility(View.GONE);
                if(!(GuruLapor.this.isDetached() || GuruLapor.this.isRemoving() || GuruLapor.this.getView() == null)){
                    Log.e(TAG,t.getMessage() + "");
                    Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });


        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final String[] options = {"Kirim Pesan"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Pilih aksi");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //do something
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        return view;
    }
}
