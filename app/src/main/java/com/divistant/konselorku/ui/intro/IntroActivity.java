package com.divistant.konselorku.ui.intro;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import com.divistant.konselorku.R;
import com.divistant.konselorku.auth.ui.login.LoginActivity;
import com.github.paolorotolo.appintro.AppIntro;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(new IntroOne());
        addSlide(new IntroTwo());
        addSlide(new IntroThree());
        addSlide(new IntroFour());
        addSlide(new IntroFive());

        showSkipButton(false);
        showStatusBar(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(false);
    }

    @Override
    public void setIndicatorColor(int selectedIndicatorColor, int unselectedIndicatorColor) {
        super.setIndicatorColor( R.color.colorAccent,R.color.colorPrimary);
    }

    @Override
    public void setDoneText(@Nullable CharSequence text) {
        super.setDoneText("Selesai");
    }

    @Override
    public void setColorDoneText(int colorDoneText) {
        super.setColorDoneText(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void setColorSkipButton(int colorSkipButton) {
        super.setColorSkipButton(getResources().getColor(R.color.colorPrimary));
    }



    @Override
    public void setNextArrowColor(int color) {
        super.setNextArrowColor(getResources().getColor(R.color.colorPrimary));
    }



    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();
    }
}
