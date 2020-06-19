package com.divistant.konselorku;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.divistant.konselorku.ui.chat.ChatFragment;
import com.divistant.konselorku.ui.dashboard.DashboardFragment;
import com.divistant.konselorku.ui.guru.GuruFragment;
import com.divistant.konselorku.ui.lapor.LaporFragment;
import com.divistant.konselorku.ui.profil.ProfileFragment;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    public MainViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new DashboardFragment();
            case 1:
                return new GuruFragment();
            case 2:
                return new LaporFragment();
            case 3:
                return new ChatFragment();
            case 4:
                return new ProfileFragment();
            default:
                Log.e("VP ADAPTER","DEFAULT FRAGMENT CALLED");
                return new DashboardFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
