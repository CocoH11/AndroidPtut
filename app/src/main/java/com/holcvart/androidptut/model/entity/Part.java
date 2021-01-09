package com.holcvart.androidptut.model.entity;

public class Part extends Entity{
    private String naming;
    private double dealPrice;
    private double billPrice;
    private int quantity;

    public Part(){
        super();
    }

    public Part(String naming, double dealPrice, double billPrice, int quantity){
        this(-1, naming, dealPrice, billPrice, quantity);
    }

    public Part(long id, String naming, double dealPrice, double billPrice, int quantity) {
        super(id);
        this.naming = naming;
        this.dealPrice = dealPrice;
        this.billPrice = billPrice;
        this.quantity = quantity;
    }

    public String getNaming() {
        return naming;
    }

    public void setNaming(String naming) {
        this.naming = naming;
    }

    public double getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(double dealPrice) {
        this.dealPrice = dealPrice;
    }

    public double getBillPrice() {
        return billPrice;
    }

    public void setBillPrice(double billPrice) {
        this.billPrice = billPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
