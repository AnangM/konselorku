package com.divistant.konselorku.ui.lapor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LaporViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public LaporViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Lapor");
    }

    public LiveData<String> getText(){return mText;}
}
