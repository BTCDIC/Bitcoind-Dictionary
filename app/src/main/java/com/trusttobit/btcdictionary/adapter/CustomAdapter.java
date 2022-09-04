package com.trusttobit.btcdictionary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.trusttobit.btcdictionary.R;
import com.trusttobit.btcdictionary.model.DataModel;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener {

    private ArrayList<DataModel> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView txtName;
    }

    public CustomAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.list_row, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        DataModel dataModel = (DataModel) object;

        switch (v.getId()) {

        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataModel dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getName());
        return convertView;
    }
}
