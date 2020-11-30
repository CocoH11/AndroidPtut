package com.holcvart.androidptut.view.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.holcvart.androidptut.model.repository.ClientRepository;

public class ClientViewModelFactory implements ViewModelProvider.Factory {
    private ClientRepository clientRepository;
    public ClientViewModelFactory(ClientRepository clientRepository){
        this.clientRepository=clientRepository;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ClientViewModel.class)) {
            return (T)new ClientViewModel(clientRepository);
        }
        else throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
