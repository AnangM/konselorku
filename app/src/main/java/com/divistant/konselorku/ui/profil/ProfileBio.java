package com.divistant.konselorku.ui.profil;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.divistant.konselorku.R;
import com.divistant.util.GetProfile;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class ProfileBio extends Fragment {

    private SharedPreferences pref;
    SimpleDateFormat sfd1 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sfd2 = new SimpleDateFormat("dd MMMM yyyy");
    TextView gen,ph, addr,dob;

    public ProfileBio() {
        // Required empty public constructor
    }

    public static ProfileBio newInstance(String param1, String param2) {
        ProfileBio fragment = new ProfileBio();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_profile_bio, container, false);
        dob = (TextView) view.findViewById(R.id.bio_dob);
        addr =(TextView) view.findViewById(R.id.bio_address);
        ph =(TextView) view.findViewById(R.id.bio_phone);
        gen =(TextView) view.findViewById(R.id.bio_gender);
        pref = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()).getApplicationContext());


        pref.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setData();
            }
        });

        setData();

        return view;

    }

    private void setData (){
        ProfilModel model = new Gson().fromJson(pref.getString("PROFILE",null),ProfilModel.class);
        if(model != null){
            try {
                Date date = sfd1.parse(model.getDob());
                dob.setText(sfd2.format(date));
            } catch (ParseException e) {
                dob.setText("~");
                e.printStackTrace();
            }

            addr.setText(model.getAddress());
            ph.setText(model.getPhone());
            if(model.getGender().equals("M")){
                gen.setText("Laki-laki");
            }else{
                gen.setText("Perempuan");
            }
        }
    }
}
