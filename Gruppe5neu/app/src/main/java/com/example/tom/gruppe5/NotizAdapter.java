package com.example.tom.gruppe5;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom on 02.02.2017.
 */

public class NotizAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Notizen> notizList;

    public NotizAdapter(ArrayList<Notizen> notizList, Context context) {

        this.notizList = notizList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return notizList.size();
    }

    @Override
    public Object getItem(int position) {
        return notizList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View ListView = View.inflate(context, R.layout.notizen, null);

        TextView textView = (TextView) ListView.findViewById(R.id.notizen_textView);


        textView.setText(notizList.get(position).getName());

        /*LinearLayout eintrag_linlay = (LinearLayout) ListView.findViewById(R.id.eintrag_LinLay);
        eintrag_linlay.addView(titleView, 0);*/

        return textView;
    }
}

