package com.inteliment.pjwin.naviapp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inteliment.pjwin.naviapp.R;
import com.inteliment.pjwin.naviapp.config.MAP;
import com.inteliment.pjwin.naviapp.model.bean.LocationData;
import com.inteliment.pjwin.naviapp.presenter.LocationDataPresenter;
import com.inteliment.pjwin.naviapp.util.L;
import com.inteliment.pjwin.naviapp.view.adapter.HeaderItemAdapter;
import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.data.BeamDataActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@RequiresPresenter(LocationDataPresenter.class)
public class MainActivity extends BeamDataActivity<LocationDataPresenter, LocationData> implements OnMapReadyCallback {
    @Bind(R.id.spinner_location) Spinner mLocationSelect;
    @Bind(R.id.panel_map) FrameLayout mMapLayout;
    @Bind(R.id.tv_car) TextView mCarTv;
    @Bind(R.id.tv_train) TextView mTrainTv;

    private List<LocationData> mDataList;
    private LocationData currentItem;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initMap();
    }

    public void onDataLoaded(List<LocationData> dataList) {
        mDataList = dataList;
        initSpinner();
    }

    public void showLoadDataError(String errorInfo) {
        L.toastShort(this, errorInfo);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getPresenter().loadData();//although highly unlikely, this will avoid interaction with map before it's ready
    }

    @OnClick(R.id.btn_navi)
    void navigateOnClick(Button btn) {
        navigateOnMap();
    }

    private void initSpinner() {
        final HeaderItemAdapter dataAdapter = new HeaderItemAdapter(this, mDataList);
        mLocationSelect.setAdapter(dataAdapter);
        mLocationSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentItem = dataAdapter.getItem(position);
                updateInfoPanel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateInfoPanel() {
        if (currentItem != null) {
            mCarTv.setText(currentItem.getFromCentralData().getCarDistanceMinute());
            mTrainTv.setText(currentItem.getFromCentralData().getTrainDistanceMinute());
        }
    }

    private void initMap() {
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        fragment.getMapAsync(this);
    }

    private void navigateOnMap() {
        if (mMap == null) {
            L.toastShort(this, R.string.error_map_not_ready);
            return;
        }

        if (currentItem == null) {
            L.toastShort(this, R.string.error_data_not_avail);
            return;
        }

        mMapLayout.setVisibility(View.VISIBLE);
        /*if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mMapLayout.setLayoutParams(new RelativeLayout.LayoutParams(mMapLayout.getMeasuredWidth(), mMapLayout.getMeasuredWidth()));//set height equals to width
        }*/

        LatLng pos = new LatLng(currentItem.getGeoInfoData().getLatitude(), currentItem.getGeoInfoData().getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(MAP.MAP_ZOOM));
        mMap.addMarker(new MarkerOptions().position(pos));

        //mMap.moveCamera();
    }
}
