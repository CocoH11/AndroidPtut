package com.holcvart.androidptut.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Deal extends Entity{
    private String date;
    private Provider provider;
    private List<PartsStored> deals;

    public Deal(){
        super();
    }

    public Deal(String date, Provider provider){
        this(-1, date, provider, new ArrayList<PartsStored>());
    }

    public Deal(long id, String date, Provider provider, List<PartsStored> deals){
        super(id);
        this.date = date;
        this.provider = provider;
        this.deals = deals;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public List<PartsStored> getDeals() {
        return deals;
    }

    public void setDeals(List<PartsStored> deals) {
        this.deals = deals;
    }
}
