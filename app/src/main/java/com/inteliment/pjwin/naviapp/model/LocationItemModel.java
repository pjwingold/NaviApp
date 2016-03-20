package com.inteliment.pjwin.naviapp.model;

import com.inteliment.pjwin.naviapp.config.API;
import com.inteliment.pjwin.naviapp.model.api.DataCallback;
import com.inteliment.pjwin.naviapp.model.bean.LocationData;
import com.jude.beam.model.AbsModel;
import com.jude.http.RequestManager;

import java.util.List;

/**
 * Created by hans on 20-Mar-16.
 */
public class LocationItemModel extends AbsModel {
    public static LocationItemModel getInstance() {
        return getInstance(LocationItemModel.class);
    }

    public void getLocationItemList(DataCallback<List<LocationData>> callback) {
        StringBuilder sb = new StringBuilder(API.URL.LOCATION_DATA.length() * 3);//use SB in case needs parameter later, presetting length is for performance
        sb.append(API.URL.LOCATION_DATA);

        RequestManager.getInstance().get(sb.toString(), callback);
    }
}
