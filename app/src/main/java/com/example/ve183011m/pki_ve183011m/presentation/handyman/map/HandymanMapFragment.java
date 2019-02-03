package com.example.ve183011m.pki_ve183011m.presentation.handyman.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ve183011m.pki_ve183011m.R;
import com.example.ve183011m.pki_ve183011m.data.RequestManager;
import com.example.ve183011m.pki_ve183011m.model.Request;
import com.example.ve183011m.pki_ve183011m.model.User;
import com.example.ve183011m.pki_ve183011m.util.PermissionUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HandymanMapFragment extends Fragment implements OnRequestPermissionsResultCallback, GoogleMap.OnInfoWindowClickListener {

    MapView mMapView;
    private GoogleMap googleMap;

    private User user;
    private static final String USER = "user";

    private List<Request> requests = new ArrayList<>();

    private HandymanMapFragmentCallback mListener;

    public HandymanMapFragment() {
        // Required empty public constructor
    }

    public static HandymanMapFragment newInstance(User user) {
        HandymanMapFragment fragment = new HandymanMapFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            user = (User) getArguments().getSerializable(USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_handyman_map, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.setOnInfoWindowClickListener(HandymanMapFragment.this);

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    PermissionUtils.requestPermission((AppCompatActivity) getActivity(), 1256, android.Manifest.permission.ACCESS_FINE_LOCATION, false);
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                updateMapUI();
            }
        });

        return rootView;
    }

    public void updateMapUI() {
        if (googleMap == null)
            return;

        googleMap.clear();

        requests = RequestManager.getInstance().getRequestsForHandyman(user);
        Geocoder geocoder = new Geocoder(getContext());
        List<LatLng> locations = new ArrayList<>();

        for (Request request : requests) {
            Address address = null;
            try {
                address = geocoder.getFromLocationName(request.getAddress(), 1).get(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (address == null)
                continue;

            double latitude = address.getLatitude();
            double longitude = address.getLongitude();

            LatLng location = new LatLng(latitude, longitude);
            locations.add(location);
            Marker marker = googleMap.addMarker(new MarkerOptions().position(location).title(request.getAddress()).snippet(request.getJob().getName()));
            marker.setTag(request);
        }

        if (locations.size() == 0)
            return;

        CameraPosition cameraPosition = new CameraPosition.Builder().target(locations.get(0)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (!PermissionUtils.isPermissionGranted(permissions, grantResults, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(getContext(), "No permission", Toast.LENGTH_LONG).show();
        } else {
            updateMapUI();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HandymanMapFragmentCallback) {
            mListener = (HandymanMapFragmentCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HandymanMapFragmentCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateMapUI();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        mListener.onRequestSelected((Request) marker.getTag());
    }

    public interface HandymanMapFragmentCallback {
        void onRequestSelected(Request request);
    }
}
