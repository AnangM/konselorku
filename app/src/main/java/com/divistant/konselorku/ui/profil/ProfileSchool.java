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

import com.divistant.konselorku.R;
import com.divistant.util.GetProfile;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileSchool#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSchool extends Fragment {
    private SharedPreferences pref;

    private TextView sch, sch_addr, classes;
    public ProfileSchool() {
        // Required empty public constructor
    }

    public static ProfileSchool newInstance(String param1, String param2) {
        ProfileSchool fragment = new ProfileSchool();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_profile_school, container, false);
        sch = (TextView) view.findViewById(R.id.sch_name);
        sch_addr = (TextView) view.findViewById(R.id.sch_addr);
        classes =(TextView) view.findViewById(R.id.sch_class);
        pref = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()).getApplicationContext());
        setData();
        pref.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                setData();
            }
        });

        return view;
    }

    public void setData(){
        ProfilModel model = new Gson().fromJson(pref.getString("PROFILE",null),ProfilModel.class);
        if(model != null) {
            sch.setText(model.getSchool_name());
            sch_addr.setText(model.getSchool_address());
            if(model.getClass_name() == null){
                classes.setText("~");
            }else{
                classes.setText(model.getClass_grade() +" "+model.getClass_name());
            }
        }
    }
}
