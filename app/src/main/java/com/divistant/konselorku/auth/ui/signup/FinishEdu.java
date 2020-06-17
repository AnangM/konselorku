package com.divistant.konselorku.auth.ui.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.divistant.konselorku.MainActivity;
import com.divistant.konselorku.R;
import com.divistant.konselorku.auth.ui.login.UserModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinishEdu extends AppCompatActivity {
    Button doneBtn;
    SharedPreferences pref;
    List<SchoolModel> schoolModelList;
    List<MClassesModel> classModelList;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> mClassAdapter;
    List<String> schoolSpinner = new ArrayList<>();
    List<String> mClassSpinnerItem = new ArrayList<>();
    Spinner sItem;
    Spinner mClassSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_edu);

        doneBtn = (Button) findViewById(R.id.edu_finish);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        schoolModelList = new ArrayList<>();
        fillSchool();

        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, schoolSpinner
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItem = (Spinner) findViewById(R.id.edu_sekolah);
        sItem.setAdapter(adapter);

        mClassAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                mClassSpinnerItem);
        mClassAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mClassSpinner = (Spinner) findViewById(R.id.edu_kelas);
        mClassSpinner.setAdapter(mClassAdapter);


        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishEdu();
            }
        });

        sItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fillClasses(position);
                mClassAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void finishEdu(){
        int school = sItem.getSelectedItemPosition();
        int mClassPos = mClassSpinner.getSelectedItemPosition();

        final SignupInterface service = SignupApi.getClient().create(SignupInterface.class);
        Map<String, Object> jsonParam = new ArrayMap<>();
        jsonParam.put("m_classes_id",schoolModelList.get(school).getId());
        jsonParam.put("school_id",classModelList.get(mClassPos).getId());

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
        Call<List<SchoolModel>> call = service.getSchool(pref.getString("TOKEN","def"));
        call.enqueue(new Callback<List<SchoolModel>>() {
            @Override
            public void onResponse(Call<List<SchoolModel>> call, Response<List<SchoolModel>> response) {
                if(response.code() == 200){
                    schoolModelList = response.body();
                    for(SchoolModel school : schoolModelList){
                        schoolSpinner.add(school.getName());
                    }
                }else{
                    Toast.makeText(getApplicationContext(),
                            String.valueOf(response.code()),
                            Toast.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<List<SchoolModel>> call, Throwable t) {
                Log.e("[FEdu]",t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(),Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    public void fillClasses(int pos){
        final SignupInterface service = SignupApi.getClient().create(SignupInterface.class);
        Call<List<MClassesModel>> call = service.getMClasses(
                pref.getString("TOKEN","def"),
                String.valueOf(pos));
        call.enqueue(new Callback<List<MClassesModel>>() {
            @Override
            public void onResponse(Call<List<MClassesModel>> call, Response<List<MClassesModel>> response) {
                if(response.code() == 200){
                    classModelList = response.body();
                    for(MClassesModel mClass : classModelList){
                        mClassSpinnerItem.add(mClass.getName());
                    }
                }else{
                    Toast.makeText(getApplicationContext(),
                            String.valueOf(response.code()),
                            Toast.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<List<MClassesModel>> call, Throwable t) {
                Log.e("[FEdu]",t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(),Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}
