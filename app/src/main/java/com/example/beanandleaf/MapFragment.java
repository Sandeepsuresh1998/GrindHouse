package com.example.beanandleaf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;

    SupportMapFragment mapFragment;
    public MapFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("On Create View");
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map,  mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.map);
        if(mMapView != null) {
            System.out.println("Hi");
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        System.out.println("Made it");
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(34.026, -118.277)).title("Starbucks"));
        CameraPosition Starbucks = CameraPosition.builder().target(new LatLng(34.026, -118.277)).zoom(16).bearing(0).tilt(45).build();
        //mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
//        {
//            @Override
//            public void onInfoWindowClick(Marker arg0) {
//                // call an activity(xml file)
//                Intent I = new Intent(this, BusInfo.class);
//                startActivity(I);
//            }
//
//        });

        //String contentString =
        //var infoWindow = new google.maps.InfoWindow({});

        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Starbucks));

        System.out.println("Done");
    }

}