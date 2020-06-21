package com.divistant.konselorku.ui.guru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.divistant.konselorku.R;
import com.divistant.net.API;
import com.divistant.net.GuruInterface;
import com.divistant.util.GeneralResponse;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;

public class GuruDetail extends AppCompatActivity {
    private GuruModel guru;

    ImageView avatar,chat,email;
    TextView name, title, about,edu,work,image_txt,err;
    LinearLayout image_txt_container;
    ProgressBar loading;
    ConstraintLayout container;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_detail);
        Intent i = getIntent();
        guru = new Gson().fromJson(i.getStringExtra("guru"),GuruModel.class);

        avatar = (ImageView) findViewById(R.id.guru_detail_image);
        image_txt_container = (LinearLayout) findViewById(R.id.guru_detail_img_text_container);
        image_txt = (TextView) findViewById(R.id.guru_detail_img_text);

        chat = (ImageView) findViewById(R.id.guru_detail_send_chat);
        email = (ImageView) findViewById(R.id.guru_detail_send_mail);

        name = (TextView) findViewById(R.id.guru_detail_name);
        title = (TextView) findViewById(R.id.guru_detail_title);
        about = (TextView) findViewById(R.id.guru_detail_about);
        edu = (TextView) findViewById(R.id.guru_detail_edu);
        work  = (TextView) findViewById(R.id.guru_detail_work);
        err = (TextView) findViewById(R.id.guru_detail_err) ;

        loading = (ProgressBar) findViewById(R.id.guru_detail_loading);
        container = (ConstraintLayout) findViewById(R.id.guru_layout_bottom_container);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_EMAIL,guru.getEmail());
                startActivity(i);
            }
        });

        if(TextUtils.isEmpty(guru.getAvatar())){
            avatar.setVisibility(View.GONE);
            char ava = guru.getName().toUpperCase().charAt(0);
            image_txt.setText(ava);
            image_txt.setVisibility(View.VISIBLE);
            image_txt_container.setVisibility(View.VISIBLE);
        }else{
            image_txt.setVisibility(View.GONE);
            image_txt_container.setVisibility(View.GONE);
            avatar.setVisibility(View.VISIBLE);

            Glide.with(avatar.getContext())
                    .load(guru.getAvatar())
                    .apply(RequestOptions.centerCropTransform())
                    .into(avatar);
        }

        name.setText(guru.getName());
        if(TextUtils.isEmpty(guru.getTitle())){
            title.setText("~");
        }else{
            title.setText(guru.getTitle());
        }
        if(TextUtils.isEmpty(guru.getAbout())){
            about.setText("~");
        }else{
            about.setText(guru.getAbout());
        }

        loading.setVisibility(View.VISIBLE);
        container.setVisibility(View.INVISIBLE);
        err.setVisibility(View.INVISIBLE);
        getContent();

    }

    public void getContent(){

        GuruInterface service = API.getClient().create(GuruInterface.class);

        Call<GeneralResponse<GuruDetailModel>> call = service
                .getGuruDetail(pref.getString("TOKEN","none"),guru.getId());

    }
}
