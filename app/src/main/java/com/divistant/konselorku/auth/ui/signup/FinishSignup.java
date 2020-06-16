package com.divistant.konselorku.auth.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import com.divistant.konselorku.R;

import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinishSignup extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    LinearLayout dobLayout;
    EditText name;
    EditText dob;
    EditText alamat;
    EditText phone;
    DatePickerDialog dpg;
    RadioGroup genderSelect;
    String gender;
    Button nextBtn;
    TextView errTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_signup);
        dobLayout = (LinearLayout) findViewById(R.id.finish_usia_container);
        name = (EditText) findViewById(R.id.finish_nama);
        dob = (EditText) findViewById(R.id.finish_usia);
        alamat = (EditText) findViewById(R.id.finish_alamat);
        phone = (EditText) findViewById(R.id.finish_phone);
        dpg = new DatePickerDialog(getApplicationContext(),
                FinishSignup.this,2000,01,01);
        genderSelect = (RadioGroup) findViewById(R.id.finish_gender);
        nextBtn = (Button) findViewById(R.id.finish_selesai);
        errTv = (TextView) findViewById(R.id.finish_err_v);

        dobLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpg.show();
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
                String err = checkUserData();
                if(err.equals(null)){
                    errTv.setVisibility(View.INVISIBLE);
                    finishProfile();

                }else{
                    errTv.setText(err);
                    errTv.setVisibility(View.VISIBLE);
                }


            }
        });

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

        Call<FinishProfileModel> call = service.finihsProfile(body);
        call.enqueue(new Callback<FinishProfileModel>() {
            @Override
            public void onResponse(Call<FinishProfileModel> call, Response<FinishProfileModel> response) {
                if(response.code()==200){
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
        String msg = null;
        if(name.getText().equals(null)){
            msg = "Nama tidak boleh kosong kak";
        }else if(dob.getText().equals(null)){
            msg = "Tanggal lahir tidak boleh kosong kak";
        }else if(genderSelect.getCheckedRadioButtonId() == -1){
            msg ="Jenis Kelamin tidak boleh kosong kak";
        }else if (alamat.getText().equals(null)){
            msg="Alamat tidak boleh kosong kak";
        }else if (phone.getText().equals(null)){
            msg = "Nomor HP tidak boleh kosong kak";
        }
        return msg;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dob.setText(dayOfMonth+"="+month+"-"+year);
    }
}
