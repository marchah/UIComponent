package com.marchah.example.uicomponent;

import android.app.Activity;
import android.os.Bundle;

import com.marchah.uicomponent.SelectableTypeEditTextPanel;

import java.util.LinkedHashMap;

/**
 * Created by marcha on 28/01/15.
 */
public class SelectableTypeEditTextPanel_Demo_Activity extends Activity {

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

        SelectableTypeEditTextPanel basicSTETP = (SelectableTypeEditTextPanel)findViewById(R.id.basicSTETP);
        basicSTETP.setListSelectType(listPhoneItem);

        SelectableTypeEditTextPanel defaultValueSTETP = (SelectableTypeEditTextPanel)findViewById(R.id.defaultValueSTETP);
        defaultValueSTETP.setListSelectType(listPhoneItem);
        defaultValueSTETP.addSelectableTypeEditText("Default Value 1");
        defaultValueSTETP.addSelectableTypeEditText("Default Value 2");
        defaultValueSTETP.addSelectableTypeEditText("Default Value 3");
    }
}
