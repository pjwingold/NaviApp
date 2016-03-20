package com.inteliment.pjwin.naviapp.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.inteliment.pjwin.naviapp.model.bean.LocationData;

import java.util.List;

/**
 * Created by hans on 20-Mar-16.
 */
public class HeaderItemAdapter extends ArrayAdapter<LocationData> {
    public HeaderItemAdapter(Context context, List<LocationData> objects) {
        super(context, android.R.layout.simple_spinner_item, objects);

        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View result = super.getView(position, convertView, parent);
        setText(result, position);
        return result;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View result = super.getDropDownView(position, convertView, parent);
        setText(result, position);
        return result;
    }

    private void setText(View view, int position) {
        TextView tv = (TextView) view.findViewById(android.R.id.text1);
        tv.setText(getItem(position).getName());
    }
}
