package com.marchah.uicomponent.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.marchah.uicomponent.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by marcha on 28/01/15.
 */
public class ListTypeAdapter extends SimpleAdapter {

    private int positionSelectedItem;
    private int selectedItemIndicatorColor;
    private int selectedItemTextColor;

    public ListTypeAdapter(Context context, List<HashMap<String, String>> items, int resource, String[] from, int[] to) {
        super(context, items, resource, from, to);
        positionSelectedItem = 0;
    }

    public void setSelectedItemColor(int selectedItemIndicatorColor, int selectedItemTextColor) {
        this.selectedItemIndicatorColor = selectedItemIndicatorColor;
        this.selectedItemTextColor = selectedItemTextColor;
    }

    public void setPositionSelectedItem(int positionSelectedItem) {
        this.positionSelectedItem = positionSelectedItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if (position == positionSelectedItem && parent.getChildCount() > positionSelectedItem) {
            View Item = parent.getChildAt(positionSelectedItem);

            Item.findViewById(R.id.fc_list_select_is_selectedTV).setBackgroundColor(selectedItemIndicatorColor);
            ((TextView)Item.findViewById(R.id.fc_list_select_valueTV)).setTextColor(selectedItemTextColor);
        }
        return view;
    }

}
