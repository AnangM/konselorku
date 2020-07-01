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
import android.util.ArrayMap;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.divistant.konselorku.assesment.AssesmentActivity;
import com.divistant.konselorku.auth.ui.login.LoginActivity;
import com.divistant.konselorku.auth.ui.signup.FinishEdu;
import com.divistant.konselorku.auth.ui.signup.FinishSignup;
import com.divistant.net.API;
import com.divistant.net.NotifInterface;
import com.divistant.util.GeneralResponse;
import com.divistant.util.GeneralResponseModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONObject;

import java.util.Date;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener{
    SharedPreferences pref;
    ViewPager vp;
    BottomNavigationView menu;
    TextView toolbarText;



    @Override
    public void onStart(){
        super.onStart();
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
            }else if(pref.getString("UPROGRESS","").equals(String.valueOf(3))){
                startActivity(new Intent(getApplicationContext(), AssesmentActivity.class));
                finish();
            }
        }

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("MAIN ACTIVITY", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        subscribe(task.getResult().getToken());
                        // Log and toast
                        String msg = "TOKEN = "  + token;
                        Log.d("MAIN", msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        vp = (ViewPager) findViewById(R.id.main_view_pager);
        toolbarText = (TextView) findViewById(R.id.main_toolbar_title);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        menu  = (BottomNavigationView) findViewById(R.id.menu_view);
        menu.setOnNavigationItemSelectedListener(this);
        MainViewPagerAdapter vpAdapter = new MainViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,pref);
        vp.setAdapter(vpAdapter);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 1:
                        toolbarText.setText("Konselor");
                        menu.getMenu().findItem(R.id.menu_guru).setChecked(true);
                        break;
                    case 2:
                        toolbarText.setText("Laporkan Aksi Bullying");
                        menu.getMenu().findItem(R.id.menu_lapor).setChecked(true);
                        break;
                    case 3:
                        toolbarText.setText("Chat");
                        menu.getMenu().findItem(R.id.menu_chat).setChecked(true);
                        break;
                    case 4:
                        toolbarText.setText("Profil");
                        menu.getMenu().findItem(R.id.menu_profil).setChecked(true);
                        break;
                    default:
                        toolbarText.setText("Konselorku");
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

    public void subscribe(String token){
        Map<String, Object> jsonParam = new ArrayMap<>();
        jsonParam.put("token",token);

        RequestBody body = RequestBody.create(okhttp3.MediaType
                        .parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParam))
                        .toString());

        NotifInterface service = API.getClient().create(NotifInterface.class);
        Call<GeneralResponse<GeneralResponseModel>> call = service.subscribe(pref.getString("TOKEN","def"),body);
        call.enqueue(new Callback<GeneralResponse<GeneralResponseModel>>() {
            @Override
            public void onResponse(Call<GeneralResponse<GeneralResponseModel>> call, Response<GeneralResponse<GeneralResponseModel>> response) {
                if(response.code()==201){
                    Toast.makeText(MainActivity.this, "SUBSCRIBED",Toast.LENGTH_LONG).show();
                }else{
                    GeneralResponse resp = response.body();
                    if(resp != null){
                    Toast.makeText(MainActivity.this, resp.getMessage(),Toast.LENGTH_LONG).show();
                    }else{
                    Toast.makeText(MainActivity.this, response.code() + "",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse<GeneralResponseModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage() + "",Toast.LENGTH_LONG).show();
                Log.e("MAIN",t.getMessage());
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
