package com.example.javierfernandez3.examenyony2ev;

/**
 * Created by javier.fernandez3 on 19/02/2018.
 */

public class DataHolder {
    public static DataHolder instance = new DataHolder();

    public FireBaseAdmin fireBaseAdmin;

    public DataHolder(){
        fireBaseAdmin=new FireBaseAdmin();
    }
}
