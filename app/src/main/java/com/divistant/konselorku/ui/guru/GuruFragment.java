package com.divistant.konselorku.ui.guru;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.divistant.konselorku.R;

public class GuruFragment extends Fragment {
    private GuruViewModel guruViewModel;
    public GuruFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        guruViewModel = ViewModelProviders.of(this).get(GuruViewModel.class);
        View root = inflater.inflate(R.layout.fragment_guru,container,false);
        final TextView tv = root.findViewById(R.id.guru_ph);
        guruViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tv.setText(s);
            }
        });
        return root;
    }
}
