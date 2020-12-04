package com.holcvart.androidptut;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.holcvart.androidptut.model.database.PhoneRepairManagementDBHelper;
import com.holcvart.androidptut.model.entity.Client;
import com.holcvart.androidptut.model.entity.Entity;
import com.holcvart.androidptut.model.entity.Intervention;
import com.holcvart.androidptut.model.repository.ClientRepository;
import com.holcvart.androidptut.model.repository.InterventionRepository;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //testClientEntity();
        this.database= new PhoneRepairManagementDBHelper(getBaseContext()).getWritableDatabase();
        emptyDatabase();
        fillDatabase();
        //fillDatabaseWithInterventions();
        //findInterventionsFromDatabase();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_client, R.id.nav_estimate, R.id.nav_intervention, R.id.nav_order)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    //method to fill the database when the device memory has been wiped out
    public void fillDatabase(){
        Client client1= new Client("Corentin", "Holcvart", "corentin.holcvart@hotmail.fr", "0770447108", "adresse");
        Client client2= new Client("Nicolas", "Boehrer", "nicolas.boehrer@edu.univ-fcomte.fr", "1234567", "je sais plus");
        Client client3= new Client("Youssef", "Raïss", "youssef.raiss@edu.univ-fcomte.fr", "2345678", "je sais pas");
        ClientRepository repository= new ClientRepository(database);
        InterventionRepository interventionRepository= new InterventionRepository(database);
        repository.setInterventionRepository(interventionRepository);
        interventionRepository.setClientRepository(repository);
        repository.insert(client1);
        repository.insert(client2);
        repository.insert(client3);
        Client newClient= new Client("nouveau", "nouveau", "nouveau", "0770447108", "address");
        Intervention intervention1= new Intervention("Intervention1", "11-11-2000", "description", false, false, newClient);

        Intervention intervention2= new Intervention("Intervention2", "11-11-2000", "description", false, false, client1);
        interventionRepository.insert(intervention1);
        interventionRepository.insert(intervention2);

        Log.d("clientIntervention", intervention1.getClient().getInterventions().get(0).getTitle());
        Log.d("clientIntervention2", String.valueOf(intervention2.getClient().getId()));
        Log.d("clientIntervention2", intervention2.getClient().getName());
    }

    public void emptyDatabase(){
        ClientRepository clientRepository= new ClientRepository(database);
        InterventionRepository interventionRepository = new InterventionRepository(database);
        clientRepository.setInterventionRepository(interventionRepository);
        interventionRepository.setClientRepository(clientRepository);
        clientRepository.deleteAll();
    }

    public void findInterventionsFromDatabase(){
        InterventionRepository interventionRepository= new InterventionRepository(database);
        List<Entity> entities=  new ArrayList<>();
        interventionRepository.findAll(entities);
        for (Entity entity:entities) {
            Intervention intervention= (Intervention)entity;
            Log.d("Intervention"+intervention.getId(), String.valueOf(intervention.getId()));
            Log.d("Intervention"+intervention.getId(), intervention.getTitle());
            Log.d("Intervention"+intervention.getId(), intervention.getDate());
            Log.d("Intervention"+intervention.getId(), intervention.getDescription());
            Log.d("Intervention"+intervention.getId(), intervention.getClient().getName());
        }
    }

    public void testClientEntity(){
        System.out.println("hhelllokoooaihigfkhzgvedfjhgzved");
        Client client1 = new Client("Corentin", "Holcvart", "corentin.holcvart@hotmail.fr", "0770447108", "address");
        Client client2 = new Client("Nicolas", "Boehrer", "nicolas.boehrer@edu.univ-fcomte.fr", "123456789", "addressNico");

        Intervention interventionClient1 = new Intervention("title", "date", "description", false, false, client1);
        Intervention interventionClient2 = new Intervention("title2", "date", "description", false, false);

        Log.d("client1", client1.getName());
        Log.d("client1", client1.getInterventions().get(0).getTitle());
        Log.d("client1Intervention", interventionClient1.getTitle());

        client2.addInterventions(interventionClient2);

        Log.d("client2", client2.getName());
        Log.d("client2", client2.getInterventions().get(0).getTitle());
        Log.d("client2Intervention", interventionClient2.getTitle());





    }
}