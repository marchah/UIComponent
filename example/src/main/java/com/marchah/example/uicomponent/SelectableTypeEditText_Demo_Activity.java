package com.marchah.example.uicomponent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.marchah.uicomponent.SelectableTypeEditText;

import java.util.LinkedHashMap;

/**
 * Created by marcha on 28/01/15.
 */
public class SelectableTypeEditText_Demo_Activity extends Activity {

    SelectableTypeEditText emptySTET;
    SelectableTypeEditText basicSTET;
    SelectableTypeEditText basicColoredSTET;
    SelectableTypeEditText emailSTET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectabletypeedittext_demo);

        emptySTET = (SelectableTypeEditText)findViewById(R.id.emptySTET);

        LinkedHashMap<Integer, String> listPhoneItem = new LinkedHashMap<Integer, String>();
        listPhoneItem.put(1, "Home");
        listPhoneItem.put(2, "Pro");
        listPhoneItem.put(3, "Mobile");
        listPhoneItem.put(4, "Fax");
        listPhoneItem.put(5, "Pager");
        listPhoneItem.put(6, "Other");

        basicSTET = (SelectableTypeEditText)findViewById(R.id.basicSTET);
        basicSTET.setListSelectType(listPhoneItem);

        basicColoredSTET = (SelectableTypeEditText)findViewById(R.id.basicColoredSTET);
        basicColoredSTET.setListSelectType(listPhoneItem, listPhoneItem.size()-1);

        LinkedHashMap<Integer, String> listEmailItem = new LinkedHashMap<Integer, String>();
        listEmailItem.put(1, "Home");
        listEmailItem.put(2, "Pro");

        emailSTET = (SelectableTypeEditText)findViewById(R.id.emailSTET);
        emailSTET.setListSelectType(listEmailItem);
        emailSTET.setFieldText("test@gmail.com");
    }

    public void GetValue(View view) {
        ((TextView)findViewById(R.id.emptyTV)).setText(emptySTET.getData().toString());
        ((TextView)findViewById(R.id.basicTV)).setText(basicSTET.getData().toString());
        ((TextView)findViewById(R.id.basicColoredTV)).setText(basicColoredSTET.getData().toString());
        ((TextView)findViewById(R.id.emailTV)).setText(emailSTET.getData().toString());
    }
}
