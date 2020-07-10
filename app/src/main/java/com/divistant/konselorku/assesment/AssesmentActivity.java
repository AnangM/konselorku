package com.divistant.konselorku.assesment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.divistant.konselorku.MainActivity;
import com.divistant.konselorku.R;
import com.divistant.konselorku.auth.ui.login.UserModel;
import com.divistant.konselorku.auth.ui.signup.FinishEdu;
import com.divistant.net.API;
import com.divistant.net.AssesmentInterface;
import com.divistant.util.GeneralResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssesmentActivity extends AppCompatActivity {

    SharedPreferences pref;
    TextView quest;
    EditText ans;
    ProgressBar progressBar;
    Button btn;
    AlertDialog loadingdialog;

    int position = 1, singleProg;
    private List<QuestionModel> questions = new ArrayList<>();
    private List<AnswerModel> answers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assesment);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        quest = (TextView) findViewById(R.id.ass_quest);
        ans = (EditText) findViewById(R.id.ass_ans);
        btn = (Button) findViewById(R.id.ass_btn);
        progressBar = (ProgressBar) findViewById(R.id.ass_prog);

        final AlertDialog.Builder loading = new AlertDialog.Builder(this);
        LayoutInflater inflater1 = getLayoutInflater();
        loading.setView(inflater1.inflate(R.layout.general_loading,null));
        loading.setTitle("Memuat...");
        loadingdialog = loading.create();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position < questions.size()){
                    AnswerModel answer = new AnswerModel(
                            questions.get(position).getId(),
                            pref.getString("UID","def"),
                            ans.getText().toString()
                    );
                    answers.add(answer);
                }
                position +=1;
                if(position < questions.size()){
                    if(position + 1 == questions.size()){
                        progressBar.setProgress(100);
                        btn.setText("Selesai");
                    }else{
                    progressBar.setProgress(progressBar.getProgress() + singleProg);
                    }
                    QuestionModel mQuest = questions.get(position);
                    quest.setText(mQuest.getQuestion());
                    ans.setText("");
                }else if(position >= questions.size()){
                    doAnswer();
                }
            }
        });


        getQuestion();

    }

    private void getQuestion(){
        AssesmentInterface service = API.getClient().create(AssesmentInterface.class);
        Call<GeneralResponse<QuestionModel>> call = service.get(pref.getString("TOKEN","def"));
        loadingdialog.show();
        call.enqueue(new Callback<GeneralResponse<QuestionModel>>() {
            @Override
            public void onResponse(Call<GeneralResponse<QuestionModel>> call, Response<GeneralResponse<QuestionModel>> response) {
                loadingdialog.dismiss();
                if (response.code() == 200){
                    GeneralResponse<QuestionModel> resp = response.body();
                    questions = resp.getData();
                    singleProg = 1;
                    progressBar.setMax(questions.size());
                    progressBar.setProgress(1);
                    position = 0;
                    QuestionModel mquest = questions.get(0);
                    quest.setText(mquest.getQuestion());
                }else{
                    Log.e("ASSESMENT",response.code() +"");
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse<QuestionModel>> call, Throwable t) {
                loadingdialog.dismiss();
                Log.e("ASSESMENT",t.getMessage());
                Toast.makeText(AssesmentActivity.this, t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doAnswer(){
        Map<String, Object> jsonParam = new ArrayMap<>();
        jsonParam.put("answers",answers);
        Log.e("ASSESMENT",new JSONObject(jsonParam).toString());
        RequestBody body = RequestBody.create(okhttp3.MediaType
                        .parse("application/json; charset=utf-8"),
                (new Gson().toJson(jsonParam)));

        AssesmentInterface service = API.getClient().create(AssesmentInterface.class);
        Call<GeneralResponse<UserModel>> call = service.answer(pref.getString("TOKEN","def"),body);
        loadingdialog.show();
        call.enqueue(new Callback<GeneralResponse<UserModel>>() {
            @Override
            public void onResponse(Call<GeneralResponse<UserModel>> call, Response<GeneralResponse<UserModel>> response) {
                loadingdialog.dismiss();
                if(response.code() == 201){
                    UserModel model = response.body().getData().get(0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("TOKEN","Bearer " + model.getToken());
                    editor.putString("UPROGRESS",model.getProgress());
                    editor.putString("ROLE",model.getRole_code());
                    editor.putString("UID",model.getUser_id());
                    editor.apply();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else{
                    GeneralResponse<UserModel> resp = response.body();
                    if(resp != null){
                        Log.e("[ASSESMENT]",response.raw().toString());
                        Toast.makeText(AssesmentActivity.this,
                                String.valueOf(response.code()) + resp.getMessage(),Toast.LENGTH_LONG).show();
                    }else{
                        Log.e("[ASSESMENT]",response.raw().toString());
                        Toast.makeText(AssesmentActivity.this,
                                String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<GeneralResponse<UserModel>> call, Throwable t) {
                loadingdialog.dismiss();
                Log.e("[ASSESMENT]",t.getMessage());
                Toast.makeText(AssesmentActivity.this,
                        t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
}
