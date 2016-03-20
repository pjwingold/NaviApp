package com.inteliment.pjwin.naviapp.presenter;

import com.inteliment.pjwin.naviapp.model.LocationItemModel;
import com.inteliment.pjwin.naviapp.model.api.DataCallback;
import com.inteliment.pjwin.naviapp.model.bean.LocationData;
import com.inteliment.pjwin.naviapp.view.activity.MainActivity;
import com.jude.beam.expansion.data.BeamDataActivityPresenter;

import java.util.List;

/**
 * Created by hans on 20-Mar-16.
 */
public class LocationDataPresenter extends BeamDataActivityPresenter<MainActivity, LocationData> {
    public void loadData() {
        LocationItemModel.getInstance().getLocationItemList(new DataCallback<List<LocationData>>() {
            @Override
            public void success(String info, List<LocationData> dataList) {
                getView().onDataLoaded(dataList);
            }

            @Override
            public void error(String errorInfo) {
                getView().showLoadDataError(errorInfo);
            }
        });
    }
}
