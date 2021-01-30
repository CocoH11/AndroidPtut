package com.holcvart.androidptut.model.entity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.holcvart.androidptut.model.database.PhoneRepairManagementContract;
import com.holcvart.androidptut.model.database.PhoneRepairManagementDBHelper;

import java.util.ArrayList;
import java.util.List;

public class Intervention extends Entity{
    private String title;
    private String date;
    private String description;
    private boolean isValid;
    private boolean isBilled;
    private boolean isPayed;
    private Client client;
    private List<PartsNeeded> partsNeededs;

    public Intervention(){
        super();
        partsNeededs = new ArrayList<>();
    }

    public Intervention(String title, String date, String description, boolean isValid, boolean isBilled, boolean isPayed){
        this(title, date, description, isValid, isBilled, isPayed, null, new ArrayList<PartsNeeded>());
    }

    public Intervention(String title, String date, String description, boolean isValid, boolean isBilled, boolean isPayed, Client client, List<PartsNeeded> partsNeededs) {
        this(-1, title, date, description, isValid, isBilled, isPayed, client, partsNeededs);
    }

    public Intervention(long id, String title, String date, String description, boolean isValid, boolean isBilled, boolean isPayed, Client client, List<PartsNeeded> partsNeededs){
        super(id);
        this.title = title;
        this.date = date;
        this.description = description;
        this.isValid = isValid;
        this.isBilled = isBilled;
        this.isPayed = isPayed;
        this.client = client;
        this.partsNeededs = partsNeededs;
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

    public List<PartsNeeded> getPartsNeededs() {
        return partsNeededs;
    }

    public void setPartsNeededs(List<PartsNeeded> partsNeededs) {
        this.partsNeededs = partsNeededs;
    }

    public void addPartsNeededs(int quantity, Part part){
        partsNeededs.add(new PartsNeeded(quantity, part));
    }
}
