package com.divistant.konselorku.auth.ui.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.divistant.konselorku.R;
public class FinishEdu extends AppCompatActivity {
    Button doneBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_edu);

        doneBtn = (Button) findViewById(R.id.edu_finish);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void finishEdu(){

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}
