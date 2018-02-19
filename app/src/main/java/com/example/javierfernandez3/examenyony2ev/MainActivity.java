package com.example.javierfernandez3.examenyony2ev;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MainActivityEvents mainActivityEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
class MainActivityEvents{

    MainActivity mainActivity;

    public MainActivityEvents(MainActivity mainActivity){
        this.mainActivity= mainActivity;
    }
}
