package com.marchah.uicomponent;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.marchah.uicomponent.Adapter.ListTypeAdapter;
import com.marchah.uicomponent.Object.BasicEntry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by marcha on 28/01/15.
 */
public class SelectableTypeEditText extends RelativeLayout {

    private List<HashMap<String, String>> listItem;

    private Button changeTypeB;

    private EditText fieldET;
    private String fieldHint;
    private int fieldInputType;

    private Dialog dialogListType;
    private String dialogTitle;
    private int positionSelectedItem;
    private int keySelectedItem;
    private int selectedItemIndicatorColor;
    private int selectedItemTextColor;

    public SelectableTypeEditText(Context context) {
        super(context);
        init(null);
    }

    public SelectableTypeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        listItem = new LinkedList<HashMap<String, String>>();

        HashMap<String, String> item = new HashMap<String, String>();
        item.put("key", "-1");
        item.put("value", getResources().getString(R.string.uic_selectabletypeedittext_no_type));
        listItem.add(item);

        positionSelectedItem = -1;
        keySelectedItem = -1;
        selectedItemIndicatorColor = getResources().getColor(R.color.uic_default_selected_item_list_indicator);
        selectedItemTextColor = getResources().getColor(R.color.uic_default_selected_item_list_text);
        dialogTitle = getResources().getString(R.string.uic_selectabletypeedittext_dialog_select_type_default_title);
        fieldHint = "";
        fieldInputType = -1;



        changeTypeB = new Button(getContext());
        changeTypeB.setId(R.id.uic_changeTypeB);
        LayoutParams changeTypeBLP = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        changeTypeBLP.addRule(ALIGN_PARENT_LEFT);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1)
            changeTypeBLP.addRule(ALIGN_PARENT_START);
        changeTypeB.setLayoutParams(changeTypeBLP);
        changeTypeB.setText(getResources().getString(R.string.uic_selectabletypeedittext_type_button_default));
        changeTypeB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDialogChooseType();
            }
        });
        addView(changeTypeB);

        fieldET = new EditText(getContext());
        LayoutParams fieldETLP = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fieldETLP.addRule(RIGHT_OF, changeTypeB.getId());
        fieldETLP.addRule(ALIGN_PARENT_RIGHT);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            fieldETLP.addRule(END_OF, changeTypeB.getId());
            fieldETLP.addRule(ALIGN_PARENT_END);
        }
        fieldETLP.addRule(ALIGN_BOTTOM, changeTypeB.getId());
        fieldET.setLayoutParams(fieldETLP);
        addView(fieldET);

        if (attrs != null)
            setAttrs(attrs);

        if (fieldInputType != -1)
            fieldET.setInputType(fieldInputType);
        fieldET.setHint(fieldHint);
    }

    private void setAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.uic_stylable);

        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.uic_stylable_dialogSelectedItemIndicatorColor) {
                selectedItemIndicatorColor = a.getColor(attr, R.color.uic_default_selected_item_list_indicator);
            }
            else if (attr == R.styleable.uic_stylable_dialogSelectedItemTextColor) {
                selectedItemTextColor = a.getColor(attr, R.color.uic_default_selected_item_list_text);
            }
            else if (attr == R.styleable.uic_stylable_dialogTitle) {
                dialogTitle = a.getString(attr);
            }
            else if (attr == R.styleable.uic_stylable_fieldHint) {
                fieldHint = a.getString(attr);
            }
            else if (attr == R.styleable.uic_stylable_android_inputType) {
                fieldInputType = a.getInt(attr, -1);
            }
        }
        a.recycle();
    }

    public Map.Entry<Integer, String> getData() {
        return new BasicEntry<Integer, String>(keySelectedItem, fieldET.getText().toString());
    }

    public void setFieldText(String fieldText) {
        fieldET.setText(fieldText);
    }

    public void setListSelectType(LinkedHashMap<Integer, String> listSelectType) {
        setListSelectType(listSelectType, 0);
    }

    public void setListSelectType(LinkedHashMap<Integer, String> listSelectType, int indexPreselectedType) {
        if (listSelectType.size() > 0) {
            positionSelectedItem = indexPreselectedType;
            listItem.clear();

            Iterator<Map.Entry<Integer,String>> it = listSelectType.entrySet().iterator();
            for (int i = 0; it.hasNext(); i++) {
                Map.Entry<Integer,String> entry = it.next();

                if (i == indexPreselectedType) {
                    keySelectedItem = entry.getKey();
                    changeTypeB.setText(entry.getValue());
                }

                HashMap<String, String> item = new HashMap<String, String>();
                item.put("key", String.valueOf(entry.getKey()));
                item.put("value", entry.getValue());
                listItem.add(item);
            }
        }
    }

    public void setSelectedItemIndicatorColor(int color) {
        selectedItemIndicatorColor = color;
    }

    public void setSelectedItemTextColor(int color) {
        selectedItemTextColor = color;
    }

    public void setDialogTitle(String title) {
        dialogTitle = title;
    }

    public void setFieldHint(String hint) {
        fieldHint = hint;
        fieldET.setHint(hint);
    }

    public void setFieldInputType(int inputType) {
        fieldInputType = inputType;
        fieldET.setInputType(inputType);
    }

    public void addTextChangedListener(TextWatcher tw) {
        fieldET.addTextChangedListener(tw);
    }

    public void requestFocusEndOfText() {
        requestFocus();
        fieldET.setSelection(fieldET.getText().length());
    }

    private LinearLayout createLayoutListViewType() {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        ListView listViewType = new ListView(getContext());

        ListTypeAdapter listViewItem = new ListTypeAdapter(getContext(), listItem, R.layout.uic_list_select_type_row,
                new String[] {"value"}, new int[] {R.id.uic_list_select_valueTV});

        listViewItem.setPositionSelectedItem(positionSelectedItem);
        listViewItem.setSelectedItemColor(selectedItemIndicatorColor, selectedItemTextColor);
        listViewType.setAdapter(listViewItem);

        listViewType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                if (id >= 0 && id < listItem.size() && !listItem.get(positionSelectedItem).get("key").equals("-1")) {
                    positionSelectedItem = (int)id;
                    keySelectedItem = Integer.parseInt(listItem.get(positionSelectedItem).get("key"));
                    changeTypeB.setText(listItem.get(positionSelectedItem).get("value"));
                }
                if (dialogListType != null)
                    dialogListType.dismiss();
            }
        });
        layout.addView(listViewType);
        return layout;
    }

    private void displayDialogChooseType() {
        dialogListType = new Dialog(getContext());
        dialogListType.setTitle(dialogTitle);
        dialogListType.setContentView(createLayoutListViewType());
        dialogListType.show();
        dialogListType.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        dialogListType.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("positionSelectedItem", positionSelectedItem);
        bundle.putInt("keySelectedItem", keySelectedItem);
        bundle.putString("fieldText", fieldET.getText().toString());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState (Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            positionSelectedItem = bundle.getInt("positionSelectedItem");
            keySelectedItem = bundle.getInt("keySelectedItem");
            if (positionSelectedItem != -1 && positionSelectedItem < listItem.size())
                changeTypeB.setText(listItem.get(positionSelectedItem).get("value"));
            fieldET.setText(bundle.getString("fieldText"));
            state = bundle.getParcelable("instanceState");
        }
        super.onRestoreInstanceState(state);
    }
}