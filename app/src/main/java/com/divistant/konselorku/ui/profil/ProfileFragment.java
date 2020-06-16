package com.divistant.konselorku.ui.profil;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.divistant.konselorku.R;
import com.divistant.konselorku.auth.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {
    private  ProfilViewModel profilViewModel;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        final TextView tv = view.findViewById(R.id.profile_ph);
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            tv.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
        }
        final Button sinot = view.findViewById(R.id.profile_signout);
        sinot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }
}
