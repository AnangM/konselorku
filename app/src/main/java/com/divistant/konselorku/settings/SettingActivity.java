package com.divistant.konselorku.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.divistant.konselorku.R;
import com.divistant.konselorku.auth.ui.login.LoginActivity;
import com.divistant.konselorku.auth.ui.login.LoginApi;
import com.divistant.konselorku.auth.ui.login.LoginInterface;
import com.divistant.konselorku.auth.ui.login.LogoutModel;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {
    SharedPreferences pref;
    AlertDialog.Builder loading;
    AlertDialog loadingdialog;
    ConstraintLayout account, bio, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        TextView so = (TextView) findViewById(R.id.setting_signout_btn);

        loading = new AlertDialog.Builder(this);
        LayoutInflater inflater1 = getLayoutInflater();
        loading.setView(inflater1.inflate(R.layout.general_loading,null));
        loading.setTitle("Memuat...");
        loadingdialog = loading.create();

        account = (ConstraintLayout) findViewById(R.id.set_profil);
        password = (ConstraintLayout) findViewById(R.id.set_password);
        bio = (ConstraintLayout) findViewById(R.id.set_bio);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, AccountSetting.class));
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, PasswordSetting.class));
            }
        });

        bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, BioSetting.class));
            }
        });

        so.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingdialog.show();
                doLogout();
            }
        });


    }

    private void doLogout(){
        final LoginInterface service = LoginApi.getClient().create(LoginInterface.class);
        Call<LogoutModel> call = service.doLogout(pref.getString("TOKEN","default"));
        call.enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response) {
                loadingdialog.dismiss();
                if(response.code()==200){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),
                            String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LogoutModel> call, Throwable t) {
                loadingdialog.dismiss();
                Log.e("[Logout]",t.getMessage());
                Toast.makeText(getApplicationContext(),
                        t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
