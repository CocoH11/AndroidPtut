package com.holcvart.androidptut.view.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.holcvart.androidptut.model.repository.ClientRepository;
import com.holcvart.androidptut.model.repository.EntityRepository;
import com.holcvart.androidptut.model.repository.InterventionRepository;

public class CustomViewModelFactory implements ViewModelProvider.Factory {
    private EntityRepository entityRepository;
    public CustomViewModelFactory(EntityRepository entityRepository){
        this.entityRepository=entityRepository;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ClientViewModel.class))return (T)new ClientViewModel((ClientRepository)entityRepository);
        else if (modelClass.isAssignableFrom(InterventionViewModel.class))return (T)new InterventionViewModel((InterventionRepository)entityRepository);
        else if (modelClass.isAssignableFrom(ClientCreateViewModel.class))return (T)new ClientCreateViewModel((ClientRepository)entityRepository);
        else if (modelClass.isAssignableFrom(ClientDetailViewModel.class))return (T)new ClientDetailViewModel((ClientRepository)entityRepository);
        else throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
