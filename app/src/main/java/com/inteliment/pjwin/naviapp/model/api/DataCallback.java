package com.inteliment.pjwin.naviapp.model.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.inteliment.pjwin.naviapp.NaviApp;
import com.inteliment.pjwin.naviapp.R;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hans on 20-Mar-16.
 */
public abstract class DataCallback<T> extends LinkCallback {

    @Override
    public void onRequest() {
        super.onRequest();
    }

    @Override
    public void onSuccess(String s) {
        if (s.equals("")) {
            error("There is no data available at the moment");
            return;
        }

        T data;
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        try {
            JsonArray jArray = parser.parse(s).getAsJsonArray();
            Type t = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

            if (jArray != null) {//we have JsonArray
                Class<?> listTypeClass = (Class<?>) ((ParameterizedType) t).getActualTypeArguments()[0];
                List results = new ArrayList(jArray.size());

                for (JsonElement element : jArray) {
                    results.add(gson.fromJson(element, listTypeClass));
                }
                data = (T) results;
            }
            else {//we have normal JsonObject
                data = gson.fromJson(s, t);
            }
        } catch (Exception e) {
            error(NaviApp.getInstance().getApplicationContext().getString(R.string.error_json_parse));
            return;
        }

        success("", data);
        super.onSuccess(s);
    }

    @Override
    public void onError(String s) {
        //result(-1,"Network Error");
        error(s);
        super.onError(s);
    }

    public void result(int status, String info){

    }
    public abstract void success(String info,T data);
    public void error(String errorInfo){

    }
}