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
import android.widget.Button;

import com.example.javierfernandez3.examenyony2ev.FBObjects.FBCoche;
import com.example.javierfernandez3.examenyony2ev.sqlLiteAdmin.DatabaseHandler;
import com.example.javierfernandez3.examenyony2ev.sqlLiteAdmin.sqlCoche;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseHandler databaseHandler;
    DrawerLayout drawer;
    SupportMapFragment mapFragment;
    Button btnLinked1, btnLinked2;

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

        btnLinked1 = findViewById(R.id.btnLinked1);
        btnLinked2 = findViewById(R.id.btnLinked2);

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

    public void botonLinckedClicked(View v) {
        if (v.getId() == R.id.btnLinked1) {
            Log.v("MetodoLink", "Has pulsado el boton linked 1");
        } else if (v.getId() == R.id.btnLinked2) {
            Log.v("MetodoLink", "Has pulsado el boton linked 2");
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
        if (id == R.id.action_item1) {
            return true;
        } else if (id == R.id.action_item2) {
            return true;
        } else if (id == R.id.action_item3) {
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
    GoogleMap mMap;

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
            quitarPines();
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

            try {
                agregarPinesCoches();
            } catch (Exception e) {
                FirebaseCrash.report(e);
            }

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
    public void quitarPines() {
        if (coches != null) {
            //Log.v("coches1234","Entro en borrar");
            for (int i = 0; i < coches.size(); i++) {
                FBCoche cocheTemp = coches.get(i);
                //  Log.v("coches1234","Antes del if");
                if (cocheTemp.getMarker() != null) {
                    //    Log.v("coches1234","No es el if");
                    cocheTemp.getMarker().remove();
                }
            }
        }
    }

    public void agregarPinesCoches() {
        //Por cada coche creamos un pin
        for (int i = 0; i < coches.size(); i++) {
            FBCoche cocheTemp = coches.get(i);

            LatLng PosTemp = new LatLng(cocheTemp.lat, cocheTemp.lon);
            //Creamos el Marker con la posicion de cada coche y titulo el nombre del coche
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(PosTemp);
            markerOptions.title(cocheTemp.Nombre);
            if (mMap != null) {
                Marker marker = mMap.addMarker(markerOptions);
                marker.setTag(cocheTemp);
                cocheTemp.setMarker(marker);
                if (i == 0) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PosTemp, 7));
                }
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        FBCoche coche = (FBCoche) marker.getTag();
        Log.v("SecondActivityEvents", "Pin " + coche.Nombre);
        Log.v("SecondActivityEvents", "Pin " + coche.Marca);
        Log.v("SecondActivityEvents", "Pin " + coche.Fabricado);
        Log.v("SecondActivityEvents", "Pin " + coche.lat);
        Log.v("SecondActivityEvents", "Pin " + coche.lon);
        Snackbar.make(secondActivity.fab, "Has pulsado el pin de " + coche.Nombre, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        return false;
    }
}
