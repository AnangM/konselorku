package com.divistant.konselorku.ui.profil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.divistant.konselorku.R;
import com.divistant.konselorku.auth.ui.login.LoginActivity;
import com.divistant.konselorku.auth.ui.login.LoginApi;
import com.divistant.konselorku.auth.ui.login.LoginInterface;
import com.divistant.konselorku.auth.ui.login.LogoutModel;
import com.divistant.konselorku.settings.SettingActivity;
import com.divistant.net.API;
import com.divistant.net.UserInterface;
import com.divistant.util.GeneralResponse;
import com.divistant.util.GetProfile;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {
    private  ProfilViewModel profilViewModel;
    private SharedPreferences pref;
    private TabLayout tabs;
    private ViewPager vp;
    private ImageView profilImg;
    private TextView name,sch,imgTxt;
    private LinearLayout img_con;
    private final ProfilModel profil = new ProfilModel();
    private ProfilePageAdapter madapter;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        pref = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()).getApplicationContext());
        tabs = (TabLayout) view.findViewById(R.id.profil_tab);
        vp = (ViewPager) view.findViewById(R.id.profil_vp);
        LinearLayout setting = (LinearLayout) view.findViewById(R.id.profil_settings);
        profilImg = (ImageView) view.findViewById(R.id.profil_img);
        name = (TextView) view.findViewById(R.id.profil_name);
        sch = (TextView) view.findViewById(R.id.profile_school);
        imgTxt = (TextView) view.findViewById(R.id.profil_img_txt);
        img_con = (LinearLayout) view.findViewById(R.id.profil_img_txt_con);
        tabs.addTab(tabs.newTab().setText("Biodata"));
        tabs.addTab(tabs.newTab().setText("Sekolah"));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        madapter = new ProfilePageAdapter(getChildFragmentManager(), tabs.getTabCount());
        vp.setAdapter(madapter);
        tabs.setupWithViewPager(vp);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }
        });

        GetProfile profileGetter = new GetProfile(pref.getString("TOKEN", "def"), new GetProfile.GetProfileResponse() {
            @Override
            public void onSuccess(ProfilModel mprofil) {
                if(mprofil.getAvatar() != null){
                    img_con.setVisibility(View.GONE);
                    imgTxt.setVisibility(View.GONE);
                    Glide.with(profilImg.getContext())
                            .load(mprofil.getAvatar())
                            .apply(RequestOptions.centerCropTransform())
                            .into(profilImg);
                }else{
                    img_con.setVisibility(View.VISIBLE);
                    char name = mprofil.getName().toUpperCase().charAt(0);
                    imgTxt.setText(String.valueOf(name));
                    imgTxt.setVisibility(View.VISIBLE);
                    profilImg.setVisibility(View.INVISIBLE);
                }

                name.setText(mprofil.getName());
                sch.setText(mprofil.getSchool_name());
            }

            @Override
            public void onFailed(String message) {
                Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
                Log.e("PROFIL",message);
            }
        });

        profileGetter.get();




//        final TextView tv = view.findViewById(R.id.profile_ph);
//
//        if(FirebaseAuth.getInstance().getCurrentUser() != null){
//            tv.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
//        }
//
//        final Button sinot = view.findViewById(R.id.profile_signout);
//
//        sinot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                doLogout();
//            }
//        });

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
