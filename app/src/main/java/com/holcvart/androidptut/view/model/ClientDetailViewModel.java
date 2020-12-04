package com.holcvart.androidptut.view.model;

import androidx.lifecycle.ViewModel;

import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.repository.ClientRepository;

public class ClientDetailViewModel extends ViewModel {
    private ClientRepository clientRepository;

    public ClientDetailViewModel(ClientRepository clientRepository){
        this.clientRepository=clientRepository;
    }

    public Client findOneById(long id){
        Client client = new Client();
        clientRepository.findOneById(id, client);
        return client;
    }
}
