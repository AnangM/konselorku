package com.divistant.konselorku.auth.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import com.divistant.konselorku.R;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinishSignup extends AppCompatActivity {
    LinearLayout dobLayout;
    EditText name;
    EditText dob;
    EditText alamat;
    EditText phone;
    RadioGroup genderSelect;
    String gender;
    Button nextBtn;
    TextView errTv;
    SharedPreferences pref;
    Button btnCal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_signup);
        dobLayout = (LinearLayout) findViewById(R.id.finish_usia_container);
        name = (EditText) findViewById(R.id.finish_nama);
        dob = (EditText) findViewById(R.id.finish_usia);
        alamat = (EditText) findViewById(R.id.finish_alamat);
        phone = (EditText) findViewById(R.id.finish_phone);
        genderSelect = (RadioGroup) findViewById(R.id.finish_gender);
        nextBtn = (Button) findViewById(R.id.finish_selesai);
        errTv = (TextView) findViewById(R.id.finish_err_v);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        btnCal = (Button) findViewById(R.id.finish_btn_cal);


        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("[DATE PICKER]","CLICKED");
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(FinishSignup.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                               setDobText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        genderSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = genderSelect.getCheckedRadioButtonId();
                RadioButton radio = (RadioButton) findViewById(selectedId);

                if(selectedId == R.id.radio_male){
                    gender="M";
                }else if(selectedId == R.id.radio_female){
                    gender="F";
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkUserData().equals("")){
                    Log.e("Click","TRUE");

                    errTv.setVisibility(View.INVISIBLE);
                    finishProfile();

                }else{
                    Log.e("Click","FALSE");
                    errTv.setText(checkUserData());
                    errTv.setVisibility(View.VISIBLE);
                }


            }
        });

    }

    private void setDobText(String text){
        dob.setText(text);
    }

    private void finishProfile(){
        final SignupInterface service = SignupApi.getClient().create(SignupInterface.class);
        Map<String, Object> jsonParam = new ArrayMap<>();
        jsonParam.put("name",name.getText());
        jsonParam.put("gender",gender);
        jsonParam.put("dob",dob.getText());
        jsonParam.put("address",alamat.getText());
        jsonParam.put("phone",phone.getText());

        RequestBody body = RequestBody.create(okhttp3.MediaType
                        .parse("application/json; charset=utf-8"),
                        (new JSONObject(jsonParam))
                        .toString());

        Call<FinishProfileModel> call = service
                .finishProfile(pref.getString("TOKEN","none"),body);

        call.enqueue(new Callback<FinishProfileModel>() {
            @Override
            public void onResponse(Call<FinishProfileModel> call, Response<FinishProfileModel> response) {
                Toast.makeText(getApplicationContext(),String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                FinishProfileModel model = response.body();
                if(response.code()==200){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("TOKEN","Bearer " + model.getToken());
                    editor.putString("UPROGRESS",model.getProgress());
                    editor.putString("ROLE",model.getRole_code());
                    editor.apply();
                    startActivity(new Intent(getApplicationContext(), FinishEdu.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<FinishProfileModel> call, Throwable t) {
                Log.e("[FProfile]",t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(),Toast.LENGTH_LONG)
                        .show();

            }
        });
    }

    private String checkUserData(){
        String msg = "";
        if(name.getText().toString().length() <= 0){
            msg = "Nama tidak boleh kosong kak";
        }else if(dob.getText().toString().length() <= 0){
            msg = "Tanggal lahir tidak boleh kosong kak";
        }else if(genderSelect.getCheckedRadioButtonId() == -1){
            msg ="Jenis Kelamin tidak boleh kosong kak";
        }else if (alamat.getText().toString().length() <= 0){
            msg="Alamat tidak boleh kosong kak";
        }else if (phone.getText().toString().length() <= 0){
            msg = "Nomor HP tidak boleh kosong kak";
        }
        return msg;
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }


}
