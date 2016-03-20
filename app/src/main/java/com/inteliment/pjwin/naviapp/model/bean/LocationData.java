package com.inteliment.pjwin.naviapp.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hans on 20-Mar-16.
 */
public final class LocationData {
    private int id;
    private String name;
    @SerializedName("fromcentral")
    private FromCentralData fromCentralData;
    @SerializedName("location")
    private GeoInfoData geoInfoData;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FromCentralData getFromCentralData() {
        return fromCentralData;
    }

    public GeoInfoData getGeoInfoData() {
        return geoInfoData;
    }

    public class FromCentralData {
        @SerializedName("car")
        private String carDistanceMinute;

        @SerializedName("train")
        private String trainDistanceMinute;

        public String getCarDistanceMinute() {
            return carDistanceMinute;
        }

        public String getTrainDistanceMinute() {
            return trainDistanceMinute;
        }
    }

    public class GeoInfoData {
        private Double latitude;
        private Double longitude;

        public Double getLatitude() {
            return latitude;
        }

        public Double getLongitude() {
            return longitude;
        }
    }
}
