package com.holcvart.androidptut.view.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.holcvart.androidptut.model.repository.ClientRepository;

public class ClientDetailsViewModelFactory implements ViewModelProvider.Factory {
    private ClientRepository clientRepository;

    public ClientDetailsViewModelFactory(ClientRepository clientRepository) {
        this.clientRepository=clientRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ClientDetailViewModel.class)) {
            return (T)new ClientDetailViewModel(clientRepository);
        }
        else throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
