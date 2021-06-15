package com.example.myapplication.ui.Eliminar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EliminarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EliminarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}