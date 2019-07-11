package com.myapplicationdev.android.project0044;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {
    Context context;
    String category[];
    LayoutInflater inflter;

    public CategoryAdapter(Context applicationContext, String[] category) {
        this.context = context;
        this.category = category;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return category.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.cat_row, null);
        TextView cat = (TextView) view.findViewById(R.id.tv);
        cat.setText(category[i]);
        return view;
    }
}
