package com.example.javierfernandez3.examenyony2ev;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by javier.fernandez3 on 19/02/2018.
 */

public interface FireBaseAdminListener {
    public void firebaseAdmin_registerOk(boolean blOk);
    public void firebaseAdmin_loginOk(boolean blOk);
    public void firebaseAdmin_ramaDescargada(String rama,DataSnapshot dataSnapshot );
}
