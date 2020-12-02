package com.holcvart.androidptut.model.entity;

public class Intervention extends Entity{
    private String title;
    private String date;
    private String description;
    private boolean isValid;
    private boolean isBilled;
    private Client client;

    public Intervention(){
        super();
    }

    public Intervention(String title, String date, String description, boolean isValid, boolean isBilled, Client client) {
        this(-1, title, date, description, isValid, isBilled, client);
    }

    public Intervention(long id, String title, String date, String description, boolean isValid, boolean isBilled, Client client){
        super(id);
        this.title=title;
        this.date=date;
        this.description=description;
        this.isValid=isValid;
        this.isBilled=isBilled;
        this.client=client;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isBilled() {
        return isBilled;
    }

    public void setBilled(boolean billed) {
        isBilled = billed;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
