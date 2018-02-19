package com.example.javierfernandez3.examenyony2ev;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

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
    //En este metodo creamos un inicio de sesion con firebase usando credenciales los
    //cuales nos los dará twitter.
    public  void Twitter(AuthCredential credential, Activity activity){
        mAuth.signInWithCredential(credential).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Log.d("firebaselogin", "signInWithEmail:success");
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    listener.firebaseAdmin_loginOk(true);

                } else {
                    // If sign in fails, display a message to the user.
                    listener.firebaseAdmin_loginOk(false);
                    Log.w(TAG, "signInWithEmail:failure", task.getException());


                }
            }
        });
    }
}
