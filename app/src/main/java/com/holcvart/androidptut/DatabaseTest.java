package com.holcvart.androidptut;

import android.app.Instrumentation;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.holcvart.androidptut.model.database.PhoneRepairManagementDBHelper;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Deal;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.model.entity.Part;
import com.holcvart.androidptut.model.entity.PartsNeeded;
import com.holcvart.androidptut.model.entity.PartsStored;
import com.holcvart.androidptut.model.entity.Provider;
import com.holcvart.androidptut.model.repository.ClientRepository;
import com.holcvart.androidptut.model.repository.DealRepository;
import com.holcvart.androidptut.model.repository.InterventionRepository;
import com.holcvart.androidptut.model.repository.PartRepository;
import com.holcvart.androidptut.model.repository.ProviderRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseTest {
    private SQLiteDatabase database;
    private Context context;
    private PartRepository partRepository;
    private ProviderRepository providerRepository;
    private DealRepository dealRepository;
    private InterventionRepository interventionRepository;
    private ClientRepository clientRepository;

    public DatabaseTest(Context context){
        database = new PhoneRepairManagementDBHelper(context).getWritableDatabase();
        this.context = context;
        partRepository = new PartRepository(database);
        providerRepository = new ProviderRepository(database);
        dealRepository = new DealRepository(database);
        interventionRepository = new InterventionRepository(database);
        clientRepository = new ClientRepository(database);
    }
    public void emptyDatabase(){
        new PhoneRepairManagementDBHelper(context).dropTables(database);
    }

    public void refreshDatabase(){
        new PhoneRepairManagementDBHelper(context).dropTables(database);
        new PhoneRepairManagementDBHelper(context).onCreate(database);
    }

    public void fillDatabase(){
        insertParts();
        insertProviders();
        insertDeals();
        insertInterventions();
        insertClient();
    }

    public void insertParts(){
        Object[][] partsArray = {
                {"vis", 15.00, 10.00, 100},
                {"screen", 10.00, 12.00, 7},
                {"speakers", 10.00, 20.00, 6},
                {"front camera", 5.00, 15.00, 12},
                {"back camera", 6.00, 10.00, 11}
        };
        for (Object[] partData:partsArray) {
            Part part = new Part((String)partData[0], (Double)partData[1], (Double)partData[2], (Integer)partData[3]);
            partRepository.insert(part);
        }
    }

    public void insertProviders(){
        Object [][] providersData = {
                {"amazon"},
                {"fnac"},
                {"auchan"},
                {"supermarché casino"}
        };

        for (Object[] providerData:providersData) {
            Provider provider = new Provider((String)providerData[0]);
            providerRepository.insert(provider);
        }
    }

    public void insertDeals(){

        Object[][] dealsData = {
                {"11-11-2000", 0, new Object[][]{{2, 0},{1, 1}}}
        };

        for (Object[] dealData:dealsData) {
            List<PartsStored> partStoredList = new ArrayList<PartsStored>();
            for (int i = 0; i < Array.getLength(dealData[2]); i++) {
                Object[][] partsStoredData = (Object[][])dealData[2];
                Part part = new Part();
                partRepository.findOneById(((Number)partsStoredData[i][0]).longValue(), part, null);
                partStoredList.add(new PartsStored(((Number)partsStoredData[i][0]).intValue(), part));
            }
            Provider provider = new Provider();
            providerRepository.findOneById(((Number)dealData[1]).longValue(), provider, null);
            Deal deal = new Deal((String)dealData[0], provider);
            deal.setDeals(partStoredList);
            dealRepository.insert(deal);
        }
    }

    public void insertInterventions(){
        Object[][] interventionsData = {
                {"Changement écran Galaxy S3", "11-11-2000", "Écran tactile qui ne fonctionne plus mais vitre intacte", false, false, new Object[][]{{5, 1}, {6, 0}}},
                {"Changement écran Galaxy S3", "11-11-2000", "Écran tactile qui ne fonctionne plus mais vitre intacte", false, false, new Object[][]{{5, 1}, {2, 0}}},
                {"Changement écran Galaxy S3", "11-11-2000", "Écran tactile qui ne fonctionne plus mais vitre intacte", false, false, new Object[][]{{5, 1}, {4, 0}}},
        };

        for (Object[] interventionData: interventionsData){
            List<PartsNeeded> partsNeededList = new ArrayList<PartsNeeded>();
            for (int i = 0; i < Array.getLength(interventionData[5]); i++) {
                Object[][] partsNeededData = (Object[][])interventionData[5];
                Part part = new Part();
                partRepository.findOneById(((Number)partsNeededData[i][1]).longValue(), part, null);
                PartsNeeded partsNeeded = new PartsNeeded(((Number)partsNeededData[i][0]).intValue(), part);
                partsNeededList.add(partsNeeded);
            }
            Intervention intervention = new Intervention((String)interventionData[0], (String)interventionData[1], (String)interventionData[2], (boolean)interventionData[3], (boolean)interventionData[4]);
            intervention.setPartsNeededs(partsNeededList);
            interventionRepository.insert(intervention);
        }
    }

    public void insertClient(){
        Object[][] clientsData = {
                {"Corentin", "Holcvart", "corentin.holcvart@hotmail.fr", "0770447108", "4 les grandes haies 70210 Fontenois la ville", new int[]{1, 2}},
                {"Audrey", "Holcvart", "audrey.holcvart@yahoo.fr", "0612440198", "4 les grandes haies 70210 Fontenois la ville", new int[]{3}}
        };

        for (Object[]clientData:clientsData) {
            List<Intervention> interventions = new ArrayList<Intervention>();
            Client client = new Client((String)clientData[0], (String)clientData[1], (String)clientData[2], (String)clientData[3], (String)clientData[4]);
            clientRepository.insert(client);
            for (int i = 0; i < Array.getLength(clientData[5]); i++){
                Intervention intervention = new Intervention();
                /*Map<String, String[]> args = new HashMap<>();
                args.put("client", new String[]{"firstName", "name"});*/
                interventionRepository.findOneById(Array.getLong(clientData[5], i), intervention, null);
                System.out.println("intervention title: "+intervention.getTitle());
                client.addInterventions(intervention);
                intervention.setClient(client);
                interventionRepository.update(intervention);
            }
            clientRepository.update(client);
        }
    }
}

