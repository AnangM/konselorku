package com.divistant.konselorku.ui.guru;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GuruViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public GuruViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Guru");
    }

    public LiveData<String> getText(){return mText;}
}
