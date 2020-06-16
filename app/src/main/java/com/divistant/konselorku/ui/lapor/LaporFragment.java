package com.divistant.konselorku.ui.lapor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.divistant.konselorku.R;

public class LaporFragment extends Fragment {
    private LaporViewModel laporViewModel;
    public LaporFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        laporViewModel = ViewModelProviders.of(this).get(LaporViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lapor,container, false);
        final TextView tv = root.findViewById(R.id.lapor_ph);
        laporViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tv.setText(s);
            }
        });
        return root;
    }
}
