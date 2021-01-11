package com.holcvart.androidptut.view.model;

import androidx.lifecycle.ViewModel;

import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.repository.InterventionRepository;

import java.util.ArrayList;
import java.util.List;

public class InterventionViewModel extends ViewModel {
    InterventionRepository interventionRepository;

    public InterventionViewModel(InterventionRepository interventionRepository) {
        this.interventionRepository = interventionRepository;
    }

    public List<Entity> findAll() {
        List<Entity> interventions= new ArrayList<>();
        interventionRepository.findAll(interventions);
        return interventions;
    }
}