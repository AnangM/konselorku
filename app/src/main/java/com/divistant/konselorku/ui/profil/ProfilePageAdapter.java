package com.divistant.konselorku.ui.profil;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProfilePageAdapter extends FragmentPagerAdapter {
    private int tabCount = 0;


    public ProfilePageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm);
        this.tabCount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new ProfileSchool();
            default:
                return new ProfileBio();
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Biodata";
            case 1:
                return "Sekolah";
            default:
                return "Err";
        }
    }
}
