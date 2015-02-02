package com.marchah.example.uicomponent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.marchah.uicomponent.Object.BasicEntry;
import com.marchah.uicomponent.SelectableTypeEditTextPanel;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by marcha on 28/01/15.
 */
public class SelectableTypeEditTextPanel_Demo_Activity extends Activity {

    SelectableTypeEditTextPanel basicSTETP;
    SelectableTypeEditTextPanel defaultValueSTETP;
    SelectableTypeEditTextPanel setValuesSTETP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectabletypeedittextpanel_demo);

        LinkedHashMap<Integer, String> listPhoneItem = new LinkedHashMap<Integer, String>();
        listPhoneItem.put(1, "Home");
        listPhoneItem.put(2, "Pro");
        listPhoneItem.put(3, "Mobile");
        listPhoneItem.put(4, "Fax");
        listPhoneItem.put(5, "Pager");
        listPhoneItem.put(6, "Other");

        basicSTETP = (SelectableTypeEditTextPanel)findViewById(R.id.basicSTETP);
        basicSTETP.setListSelectType(listPhoneItem);

        defaultValueSTETP = (SelectableTypeEditTextPanel)findViewById(R.id.defaultValueSTETP);
        defaultValueSTETP.setListSelectType(listPhoneItem);
        defaultValueSTETP.addSelectableTypeEditText("Default Value 1");
        defaultValueSTETP.addSelectableTypeEditText("Default Value 2");
        defaultValueSTETP.addSelectableTypeEditText("Default Value 3");


        List<Map.Entry<Integer, String>> listData = new LinkedList<>();
        listData.add(new BasicEntry<Integer, String>(1, "test1@email.com"));
        listData.add(new BasicEntry<Integer, String>(2, "test2@email.com"));
        listData.add(new BasicEntry<Integer, String>(4, "test3@email.com"));
        listData.add(new BasicEntry<Integer, String>(0, "test4@email.com"));
        setValuesSTETP = (SelectableTypeEditTextPanel)findViewById(R.id.setValuesSTETP);
        setValuesSTETP.setListSelectType(listPhoneItem);
        setValuesSTETP.setListSelectableTypeEditText(listData);
    }

    public void GetValue(View view) {
        ((TextView)findViewById(R.id.basicTV)).setText(basicSTETP.getData().toString());
        ((TextView)findViewById(R.id.defaultValueTV)).setText(defaultValueSTETP.getData().toString());
        ((TextView)findViewById(R.id.setValuesTV)).setText(setValuesSTETP.getData().toString());
    }
}
