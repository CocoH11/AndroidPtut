package com.holcvart.androidptut.model.entity;

public class PartsNeeded extends Entity{
    private int quantity;
    private Part part;
    private Intervention intervention;

    public PartsNeeded(){
        super();
    }

    public PartsNeeded(int quantity, Part part){
        this.quantity=quantity;
        this.part=part;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Intervention getIntervention() {
        return intervention;
    }

    public void setIntervention(Intervention intervention) {
        this.intervention = intervention;
    }
}
