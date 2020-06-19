package com.divistant.konselorku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.divistant.konselorku.auth.ui.login.LoginActivity;
import com.divistant.konselorku.auth.ui.signup.FinishEdu;
import com.divistant.konselorku.auth.ui.signup.FinishSignup;
import com.divistant.konselorku.ui.chat.ChatFragment;
import com.divistant.konselorku.ui.dashboard.ArticleClickListener;
import com.divistant.konselorku.ui.dashboard.ContentFragment;
import com.divistant.konselorku.ui.dashboard.DashboardFragment;
import com.divistant.konselorku.ui.dashboard.PostModel;
import com.divistant.konselorku.ui.guru.GuruFragment;
import com.divistant.konselorku.ui.intro.IntroActivity;
import com.divistant.konselorku.ui.lapor.LaporFragment;
import com.divistant.konselorku.ui.profil.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener{
    SharedPreferences pref;
    ViewPager vp;
    BottomNavigationView menu;



    @Override
    public void onStart(){
        super.onStart();
        Log.e("PREF",pref.getString("UPROGRESS","Default prog"));
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }else{
            if(pref.getString("UPROGRESS","").equals(String.valueOf(1))){
                startActivity(new Intent(getApplicationContext(), FinishSignup.class));
                finish();
            }else if(pref.getString("UPROGRESS","").equals(String.valueOf(2))){
                startActivity(new Intent(getApplicationContext(), FinishEdu.class));
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        vp = (ViewPager) findViewById(R.id.main_view_pager);
        menu  = (BottomNavigationView) findViewById(R.id.menu_view);
        menu.setOnNavigationItemSelectedListener(this);
        MainViewPagerAdapter vpAdapter = new MainViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vp.setAdapter(vpAdapter);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        menu.getMenu().findItem(R.id.menu_dashboard).setChecked(true);
                        break;
                    case 1:
                        menu.getMenu().findItem(R.id.menu_guru).setChecked(true);
                        break;
                    case 2:
                        menu.getMenu().findItem(R.id.menu_lapor).setChecked(true);
                        break;
                    case 3:
                        menu.getMenu().findItem(R.id.menu_chat).setChecked(true);
                        break;
                    case 4:
                        menu.getMenu().findItem(R.id.menu_profil).setChecked(true);
                        break;
                    default:
                        menu.getMenu().findItem(R.id.menu_dashboard).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_dashboard:
                vp.setCurrentItem(0);
                break;
            case R.id.menu_guru:
                vp.setCurrentItem(1);
                break;
            case R.id.menu_lapor:
                vp.setCurrentItem(2);
                break;
            case R.id.menu_chat:
                vp.setCurrentItem(3);
                break;
            case R.id.menu_profil:
                vp.setCurrentItem(4);
                break;
            default:
                vp.setCurrentItem(0);
                break;
        }
        return true;
    }
}
