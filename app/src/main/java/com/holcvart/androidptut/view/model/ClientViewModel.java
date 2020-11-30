package com.holcvart.androidptut.view.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.repository.ClientRepository;

import java.util.List;

public class ClientViewModel extends ViewModel {
    private ClientRepository clientRepository;

    public ClientViewModel(ClientRepository clientRepository) {
        this.clientRepository=clientRepository;
    }

    public List<Client> findAll(){
        return clientRepository.findAll();
    }
}