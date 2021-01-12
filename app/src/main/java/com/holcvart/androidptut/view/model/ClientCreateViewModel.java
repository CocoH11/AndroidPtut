package com.holcvart.androidptut.view.model;

import androidx.lifecycle.ViewModel;

import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.repository.ClientRepository;

public class ClientCreateViewModel extends ViewModel {
    private ClientRepository clientRepository;
    public ClientCreateViewModel(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void insert(Client client){
        clientRepository.insert(client);
    }
}