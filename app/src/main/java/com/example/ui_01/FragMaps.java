package com.example.ui_01;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragMaps extends Fragment {
    private View view;
    private FragmentManager fragmentManager;
    private MapFragment mapFragment;


    //상태 저장(어댑터와 맞물려서 통신해주는 역할)
    public static FragMaps newInstance(){
        FragMaps fragMaps = new FragMaps();
        return fragMaps;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_maps,container,false);

        return view;
    }

    public void onMapReady(GoogleMap googleMap) {
        //구글맵 준비시 호출되는 부분
        LatLng location = new LatLng(37.484298, 126.798383);//우리집 위치
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("부천 우리집");
        markerOptions.snippet("우리집");
        markerOptions.position(location);
        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,20));
    }
}
