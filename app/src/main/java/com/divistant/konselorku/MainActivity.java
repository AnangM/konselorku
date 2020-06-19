package com.divistant.konselorku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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

public class MainActivity extends AppCompatActivity{
    SharedPreferences pref;



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
        final BottomNavigationView menu =findViewById(R.id.menu_view);
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        DashboardFragment df1 = new DashboardFragment();
        ft1.replace(R.id.nav_host_fragment, df1);
        ft1.addToBackStack(null);
        ft1.commit();

        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    switch (menuItem.getItemId()){
                        case R.id.menu_dashboard:
                            DashboardFragment df = new DashboardFragment();
                            ft.replace(R.id.nav_host_fragment,df);
                            break;
                        case  R.id.menu_guru:
                            GuruFragment gf = new GuruFragment();
                            ft.replace(R.id.nav_host_fragment,gf);
                            break;
                        case R.id.menu_lapor:
                            LaporFragment lf = new LaporFragment();
                            ft.replace(R.id.nav_host_fragment, lf);
                            break;
                        case R.id.menu_chat:
                            ChatFragment cf = new ChatFragment();
                            ft.replace(R.id.nav_host_fragment, cf);
                            break;
                        case R.id.menu_profil:
                            ProfileFragment pf =new ProfileFragment();
                            ft.replace(R.id.nav_host_fragment, pf);
                            break;
                    }
                    ft.addToBackStack(null);
                    ft.commit();
                return true;
            }
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }
    }


}
