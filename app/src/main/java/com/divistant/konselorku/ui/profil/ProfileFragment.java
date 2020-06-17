package com.divistant.konselorku.ui.profil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


public class ProfileFragment extends Fragment {
    private  ProfilViewModel profilViewModel;
    private SharedPreferences pref;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final TextView tv = view.findViewById(R.id.profile_ph);

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            tv.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
        }

        final Button sinot = view.findViewById(R.id.profile_signout);

        sinot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogout();
            }
        });

        return view;
    }

    private void doLogout(){
        final LoginInterface service = LoginApi.getClient().create(LoginInterface.class);
        Call<LogoutModel> call = service.doLogout(pref.getString("TOKEN","default"));
        call.enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response) {
                if(response.code()==200){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                    getActivity().finish();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),
                            String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LogoutModel> call, Throwable t) {
                    Log.e("[Logout]",t.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
