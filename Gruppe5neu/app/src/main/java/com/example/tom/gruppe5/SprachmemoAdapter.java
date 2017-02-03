package com.example.tom.gruppe5;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tom on 02.02.2017.
 */

public class SprachmemoAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Sprachmemo> memoList;

    public SprachmemoAdapter(ArrayList<Sprachmemo> memoList, Context context) {

        this.memoList = memoList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return memoList.size();
    }

    @Override
    public Object getItem(int position) {
        return memoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View spinnerItem = View.inflate(context, R.layout.sprachmemo_spinner_item, null);

        TextView textView = (TextView) spinnerItem.findViewById(R.id.spinner_item);

        textView.setText(memoList.get(position).getName());

        return spinnerItem;
    }
}
