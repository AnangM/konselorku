package com.divistant.konselorku.auth.ui.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.divistant.konselorku.MainActivity;
import com.divistant.konselorku.R;
import com.divistant.konselorku.auth.ui.login.UserModel;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinishEdu extends AppCompatActivity {
    Button doneBtn;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_edu);

        doneBtn = (Button) findViewById(R.id.edu_finish);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishEdu();
            }
        });

    }

    private void finishEdu(){
        final SignupInterface service = SignupApi.getClient().create(SignupInterface.class);
        Map<String, Object> jsonParam = new ArrayMap<>();
        jsonParam.put("m_classes_id","");
        jsonParam.put("school_id","");

        RequestBody body = RequestBody.create(okhttp3.MediaType
                        .parse("application/json; charset=utf-8"),
                (new JSONObject(jsonParam))
                        .toString());

        Call<UserModel> call = service
                .finihsEdu(pref.getString("TOKEN","none"),body);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                UserModel model = response.body();
                if(response.code() == 200){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("TOKEN","Bearer " + model.getToken());
                    editor.putString("UPROGRESS",model.getProgress());
                    editor.putString("ROLE",model.getRole_code());
                    editor.apply();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("[FEdu]",t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fillSchool(){
        final SignupInterface service = SignupApi.getClient().create(SignupInterface.class);
        Call<SchoolModel> call = service.getSchool(pref.getString("TOKEN","def"));
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}
