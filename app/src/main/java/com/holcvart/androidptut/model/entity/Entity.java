package com.holcvart.androidptut.model.entity;

import java.util.Observable;

public abstract class Entity extends Observable {
    protected long id;
    public Entity(){}
    public Entity(long id){
        this.id=id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
