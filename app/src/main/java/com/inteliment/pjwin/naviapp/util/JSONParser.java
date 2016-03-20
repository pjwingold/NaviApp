package com.inteliment.pjwin.naviapp.util;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hans on 20-Mar-16.
 */
public class JSONParser {
    /**
     * Auto intialise all fields of the given JSON mapping entity object
     * Object field names must match the index names in the json string
     *
     * @param json Json string
     * @param classT The class to be initialised
     * @param <T> Object to be created
     * @return The an object of T from the given json string
     * @throws IOException
     * @throws JSONException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T fromJson(String json, Class<T> classT) throws IOException, JSONException,
            ClassNotFoundException, IllegalAccessException, InstantiationException {
        JSONObject jObj = new JSONObject(json);//RootResult JSON

        T result = classT.newInstance();
        Field[] fields = classT.getDeclaredFields();
        for (Field rootField : fields) {
            String name = rootField.getName();
            rootField.setAccessible(true);

            Class cType = rootField.getType();
            if (cType.isPrimitive()) {//is primitive type
                if (cType.equals(Double.TYPE)) {
                    rootField.setDouble(result, jObj.getDouble(name));
                }
                else {
                }
            }
            else {
                if (cType.equals(String.class)) {
                    rootField.set(result, jObj.getString(name));
                }
                else {
                    JSONObject jo1 = jObj.getJSONObject(name);//daily, level 1

                    Class c1 = Class.forName(rootField.getType().getCanonicalName());
                    Object o1 = c1.newInstance();// new DailyResult()
                    rootField.set(result, o1);//ie create a new DailyResult object and assign to the field

                    Field[] fields1;
                    if (c1.getSuperclass() != null) {
                        Field[] superFields = c1.getSuperclass().getDeclaredFields();
                        Field[] c1Fields = c1.getDeclaredFields();
                        fields1 = ArrayUtils.addAll(c1Fields, superFields);
                    }
                    else {
                        fields1 = c1.getDeclaredFields();
                    }
                    for (Field f1 : fields1) {
                        f1.setAccessible(true);
                        Class fType = f1.getType();
                        String name1 = f1.getName();

                        if (!fType.isPrimitive()) {
                            if (fType.equals(List.class)) {//data
                                ParameterizedType listType = (ParameterizedType) f1.getGenericType();//List<Forecast> data;
                                Class<?> listTypeClass = (Class<?>) listType.getActualTypeArguments()[0];
                                Class c2 = Class.forName(listTypeClass.getCanonicalName());
                                Field[] fields2 = c2.getDeclaredFields();
                                List listData = new ArrayList<>();

                                JSONArray data = jo1.getJSONArray(f1.getName());//data -> JSONArray
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject jo2 = data.getJSONObject(i);
                                    Object o2 = c2.newInstance();//new Forecast()
                                    for (Field f2 : fields2) {//set values on Forecast object
                                        f2.setAccessible(true);
                                        Class fType2 = f2.getType();
                                        String name2 = f2.getName();
                                        if (fType2.isPrimitive()) {
                                            if (fType2.equals(Long.TYPE)) {
                                                f2.setLong(o2, jo2.getLong(name2));
                                            }
                                        }
                                        else {
                                            if (fType2.equals(String.class)) {
                                                f2.set(o2, jo2.getString(name2));
                                            }
                                        }
                                    }
                                    listData.add(o2);//add forecast to list
                                }
                                f1.set(o1, listData);//update List<Forecast> data;
                            }
                            else if (fType.equals(String.class)) {
                                f1.set(o1, jo1.getString(name1));
                            }
                            else {
                            }
                        }
                        else {
                            if (fType.equals(Long.TYPE)) {
                                f1.setLong(o1, jo1.getLong(name1));
                            }
                        }
                    }
                }
            }
        }

        return result;
    }
}
