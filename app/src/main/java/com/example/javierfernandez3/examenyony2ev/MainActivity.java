package com.example.javierfernandez3.examenyony2ev;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class MainActivity extends AppCompatActivity {

    TwitterLoginButton loginButton;
    //este contexto lo utilizamos en el login de firebase con credenciales
    MainActivity context = this;

    Animation btnTwAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_main);

        MainActivityEvents mainActivityEvents= new MainActivityEvents(this);
        DataHolder.instance.fireBaseAdmin.setListener(mainActivityEvents);

        //Vinculamos el boton de Twitter y le seteamos el callback que es donde ira
        //cuando haga el login
        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);

            loginButton.setCallback(new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                    Log.v("HEY", "--------------->>>>>>>>>>>>>   " + result.data.getUserName());

                    //Hacemos unos credenciales ya que el metodo de firebase nos los pide y le pasamos los de twitter

                    AuthCredential credential = TwitterAuthProvider.getCredential(
                            result.data.getAuthToken().token,
                            result.data.getAuthToken().secret);
                    DataHolder.instance.fireBaseAdmin.Twitter(credential, context);


                }


                @Override
                public void failure(TwitterException exception) {
                    Log.v("ERROR","MAL CALLBACK");
                }
            });

        btnTwAnim = AnimationUtils.loadAnimation(this,R.anim.tw_anim);
        loginButton.startAnimation(btnTwAnim);


    }
    //Desde Aqui le pasamos los resultados del login al
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
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
