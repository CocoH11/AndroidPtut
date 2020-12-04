package com.holcvart.androidptut.view.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;

public class ClientViewModel extends ViewModel {
    private ClientRepository clientRepository;

    public ClientViewModel(ClientRepository clientRepository) {
        this.clientRepository=clientRepository;
    }

    public List<Entity> findAll(){
        List<Entity> clients= new ArrayList<>();
        clientRepository.findAll(clients);
        return clients;
    }
}