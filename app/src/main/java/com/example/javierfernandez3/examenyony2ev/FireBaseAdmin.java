package com.example.javierfernandez3.examenyony2ev;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by javier.fernandez3 on 19/02/2018.
 */

public class FireBaseAdmin {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    //Referencia a la raiz de la base de datos, de ahi partiremos para observar una rama
    DatabaseReference myRefRaiz;

    public FireBaseAdminListener listener;
    public FirebaseUser user;


    public FireBaseAdmin(){
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRefRaiz=database.getReference();
    }
    public void setListener(FireBaseAdminListener listener) {
        this.listener = listener;
    }
}
