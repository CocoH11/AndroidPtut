package com.holcvart.androidptut.model.entity;

public class PartsStored extends Entity{
    private int quantity;
    private Part part;
    private Deal deal;

    public PartsStored(int quantity, Part part) {
        this.quantity = quantity;
        this.part = part;
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

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }
}
