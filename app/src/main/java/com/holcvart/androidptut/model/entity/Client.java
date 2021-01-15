package com.holcvart.androidptut.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Client extends Entity{
    private String firstName;
    private String name;
    private String email;
    private String phone;
    private String address;
    private List<Intervention> interventions;

    public Client(){
        super();
        interventions= new ArrayList<>();
    }

    public Client(String firstName, String name, String email, String phone, String address) {
        this(-1, firstName, name, email, phone, address);
    }

    public Client(long id, String firstName, String name, String email, String phone, String address){
        super(id);
        this.firstName=firstName;
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.address=address;
        interventions= new ArrayList<>();
    }

    public Client(long id, String firstName, String name, String email, String phone, String address, List<Intervention> interventions){
        super(id);
        this.firstName=firstName;
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.address=address;
        this.interventions=interventions;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Intervention> getInterventions() {
        return interventions;
    }

    public void setInterventions(List<Intervention> interventions) {
        this.interventions = interventions;
    }

    public void addInterventions(Intervention intervention){
        interventions.add(intervention);
        intervention.setClient(this);
    }
}
