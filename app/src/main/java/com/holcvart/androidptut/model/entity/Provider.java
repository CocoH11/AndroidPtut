package com.holcvart.androidptut.model.entity;

public class Provider extends Entity{
    private String naming;

    public Provider(){
        super();
    }

    public Provider(String naming){
        this(-1, naming);
    }

    public Provider(long id, String naming){
        super(id);
        this.naming=naming;
    }

    public String getNaming() {
        return naming;
    }

    public void setNaming(String naming) {
        this.naming = naming;
    }
}
