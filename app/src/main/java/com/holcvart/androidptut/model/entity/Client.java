package com.holcvart.androidptut.model.entity;

public class Client extends Entity {
    private String firstName;
    private String name;
    private String email;
    private String phone;
    private String address;

    public Client(){
        super();
    }

    public Client(String firstName, String name, String email, String phone, String address) {
        this();
        this.firstName = firstName;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.id=-1;
    }

    public Client(long id, String firstName, String name, String email, String phone, String address){
        this(firstName, name, email, phone, address);
        this.id=id;
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
}
