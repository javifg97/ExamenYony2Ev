package com.example.javierfernandez3.examenyony2ev;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class MainActivity extends AppCompatActivity {

    MainActivityEvents mainActivityEvents;

    TwitterLoginButton loginButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivityEvents mainActivityEvents= new MainActivityEvents(this);
        DataHolder.instance.fireBaseAdmin.setListener(mainActivityEvents);

        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);

    }
}
class MainActivityEvents implements FireBaseAdminListener{

    MainActivity mainActivity;

    public MainActivityEvents(MainActivity mainActivity){
        this.mainActivity= mainActivity;
    }

    @Override
    public void firebaseAdmin_registerOk(boolean blOk) {

    }

    @Override
    public void firebaseAdmin_loginOk(boolean blOk) {
        if(blOk){
            Log.v("loginOk","TODO CORRECTO"+blOk);
            //ESTA PARTE DE CODIGO LO QUE HARA ES INICIAR EL SEGUNDO ACTIVITY Y FINALIZAR EL MAIN
            Intent intent= new Intent(mainActivity,SecondActivity.class);
            mainActivity.startActivity(intent);
            mainActivity.finish();

        }else{
            Log.v("LoginError","TODO MAL"+blOk);
        }
    }

    @Override
    public void firebaseAdmin_ramaDescargada(String rama, DataSnapshot dataSnapshot) {

    }
}
