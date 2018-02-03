package com.frezarin.campusparty.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.frezarin.campusparty.API.model.Event;
import com.frezarin.campusparty.R;

import java.util.List;

/**
 * Created by macbook on 03/02/2018.
 */

public class SpinnerAdapter extends BaseAdapter {
    private List<Event> mList;
    private Activity mContext;
    private LayoutInflater mInflater;

    public SpinnerAdapter(List<Event> mList, Activity mContext) {
        this.mList = mList;
        this.mContext = mContext;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            view = mInflater.inflate(R.layout.item_location, null);
        }

        String names[] = mList.get(i).title.split(" ");
        TextView txt = view.findViewById(R.id.tv_title);
        txt.setText(names[2]);

        return view;
    }
}
