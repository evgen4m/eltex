package com.esoft.eltex.presentation.fragments.detailInfoFragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.esoft.eltex.app.App;

public class DetailViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public DetailViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailViewModel(((App) application).loginRepository, ((App) application).preferenceDataSource);
    }

}
