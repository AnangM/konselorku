package com.divistant.konselorku.ui.guru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.divistant.konselorku.R;
import com.google.gson.Gson;

public class GuruDetail extends AppCompatActivity {
    private GuruModel guru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_detail);
        Intent i = getIntent();
        guru = new Gson().fromJson(i.getStringExtra("guru"),GuruModel.class);

    }
}
