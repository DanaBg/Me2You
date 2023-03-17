package com.example.me2you;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<SpinnerOption> {

    public SpinnerAdapter(Context context, List<SpinnerOption> options) {
        super(context, 0, options);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        SpinnerOption option = getItem(position);
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(option.getName());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        SpinnerOption option = getItem(position);
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(option.getName());

        return view;
    }
}
