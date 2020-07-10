package com.divistant.konselorku.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.divistant.konselorku.R;
import com.divistant.konselorku.ui.profil.ProfilModel;
import com.google.gson.Gson;

public class AccountSetting extends AppCompatActivity {
    SharedPreferences pref;
    ProfilModel userdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userdata = new Gson().fromJson(pref.getString("PROFILE","Nothing"),ProfilModel.class);

    }
}
