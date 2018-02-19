package com.example.javierfernandez3.examenyony2ev;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragmentMarker extends Fragment {

    TextView txvNombre, txvMarca, txvFabricado, txvLat, txvLon;

    public fragmentMarker() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_marker, container, false);

        txvNombre = v.findViewById(R.id.txvNombre);
        txvMarca = v.findViewById(R.id.txvMarca);
        txvFabricado = v.findViewById(R.id.txvFabricado);
        txvLat = v.findViewById(R.id.txtvLat);
        txvLon = v.findViewById(R.id.txtvLon);


        return v;
    }

}

