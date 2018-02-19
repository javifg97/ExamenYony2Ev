package com.example.javierfernandez3.examenyony2ev;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.javierfernandez3.examenyony2ev.FBObjects.FBCoche;
import com.example.javierfernandez3.examenyony2ev.sqlLiteAdmin.DatabaseHandler;
import com.example.javierfernandez3.examenyony2ev.sqlLiteAdmin.sqlCoche;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseHandler databaseHandler;
    DrawerLayout drawer;
    SupportMapFragment mapFragment;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        SecondActivityEvents events=new SecondActivityEvents(this);
        DataHolder.instance.fireBaseAdmin.setListener(events);

        databaseHandler = new DatabaseHandler(this);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMapa);
        mapFragment.getMapAsync(events);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            DataHolder.instance.fireBaseAdmin.descargarYObservarRama("Coches");
        }catch (Exception e){
            FirebaseCrash.report(e);
        }
    }

    @Override
    public void onBackPressed() {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

class SecondActivityEvents implements FireBaseAdminListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    SecondActivity secondActivity;
    ArrayList<FBCoche> coches;
    List<sqlCoche> cocheList;

    public SecondActivityEvents(SecondActivity secondActivity){
        this.secondActivity = secondActivity;
    }

    @Override
    public void firebaseAdmin_registerOk(boolean blOk) {

    }

    @Override
    public void firebaseAdmin_loginOk(boolean blOk) {

    }

    @Override
    public void firebaseAdmin_ramaDescargada(String rama, DataSnapshot dataSnapshot) {

        if(rama.equals("Coches")) {

            GenericTypeIndicator<ArrayList<FBCoche>> indicator = new GenericTypeIndicator<ArrayList<FBCoche>>() {
            };
            coches = dataSnapshot.getValue(indicator);
            //VALUES NO ES UN ARRAY LIST ES UN COLLECTIONS
            Log.v("coches", "COCHES CONTIENE: " + coches);
            FirebaseCrash.log("Rama descargada");
            int cont = 0;
            //Por cada coche hacemos un addContacto que hace un insert a la tabla.
            for (FBCoche coche: coches) {
                sqlCoche cocheaux = new sqlCoche(cont,coche.Fabricado,coche.Marca,coche.Nombre,coche.lat,coche.lon);
                this.secondActivity.databaseHandler.addContact(cocheaux);
                cont++;
            }
            FirebaseCrash.log("Tabla rellenada");


            //cojemos todos los coches y los mostramos por la consola
            cocheList = secondActivity.databaseHandler.getAllContacts();

            for (sqlCoche coche: cocheList) {
                Log.v("SQLDDBB","FABRICADO----->"+coche.getFabricado());
                Log.v("SQLDDBB","MARCA----->"+coche.getMarca());
                Log.v("SQLDDBB","NOMBRE----->"+coche.getNombre());
            }
            FirebaseCrash.log("Select ejecutado");

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
