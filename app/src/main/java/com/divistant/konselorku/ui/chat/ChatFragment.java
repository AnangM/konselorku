package com.divistant.konselorku.ui.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.divistant.konselorku.R;

public class ChatFragment extends Fragment {
    private ChatViewModel chatViewModel;
    public ChatFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        View view = inflater.inflate(R.layout.fragment_chat,container,false);
        final TextView tv = view.findViewById(R.id.chat_ph);
        chatViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tv.setText(s);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}
