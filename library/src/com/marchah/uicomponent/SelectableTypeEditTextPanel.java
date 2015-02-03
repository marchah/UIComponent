package com.marchah.uicomponent;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by marcha on 28/01/15.
 */
public class SelectableTypeEditTextPanel extends LinearLayout {

    List<SelectableTypeEditText> listSelectableTypeEditText;
    LinkedHashMap<Integer, String> listSelectType;

    int currentIndexPreselectedType;

    private int selectedItemIndicatorColor;
    private int selectedItemTextColor;
    private String dialogTitle;
    private String fieldHint;
    private int fieldInputType;

    public SelectableTypeEditTextPanel(Context context) {
        super(context);
        init(null);
    }

    public SelectableTypeEditTextPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        this.setOrientation(VERTICAL);
        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        listSelectableTypeEditText = new LinkedList<SelectableTypeEditText>();
        listSelectType = new LinkedHashMap<Integer, String>();

        selectedItemIndicatorColor = getResources().getColor(R.color.uic_default_selected_item_list_indicator);
        selectedItemTextColor = getResources().getColor(R.color.uic_default_selected_item_list_text);
        dialogTitle = getResources().getString(R.string.uic_selectabletypeedittext_dialog_select_type_default_title);
        fieldHint = "";
        fieldInputType = -1;

        if (attrs != null)
            setAttrs(attrs);

        currentIndexPreselectedType = 0;
        addSelectableTypeEditText("");
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

    public void setListSelectType(LinkedHashMap<Integer, String> listSelectType) {
        this.listSelectType.clear();
        this.listSelectType.putAll(listSelectType);
        for (SelectableTypeEditText view : listSelectableTypeEditText) {
            view.setListSelectType(listSelectType);
        }
    }

    public void addSelectableTypeEditText(String fieldText) {
        addSelectableTypeEditText(fieldText, currentIndexPreselectedType++);
    }

    public void addSelectableTypeEditText(String fieldText, int indexPreselectedType) {
        if (indexPreselectedType >= listSelectType.size())
            indexPreselectedType = listSelectType.size() - 1;
        if (indexPreselectedType < 0)
            indexPreselectedType = 0;
        final SelectableTypeEditText view = new SelectableTypeEditText(getContext());
        view.setSelectedItemIndicatorColor(selectedItemIndicatorColor);
        view.setSelectedItemTextColor(selectedItemTextColor);
        view.setDialogTitle(dialogTitle);
        view.setFieldHint(fieldHint);
        view.setFieldInputType(fieldInputType);
        view.setListSelectType(listSelectType, indexPreselectedType);
        view.setFieldText(fieldText);
        view.addTextChangedListener(new TextWatcher() {
            private boolean flag = view.getData().getValue().length() == 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (flag && s.length() > 0) {
                    addSelectableTypeEditText(null, currentIndexPreselectedType++);
                    flag = false;
                }
                else if (!flag && s.length() == 0) {
                    removeSelectableTypeEditText(view);
                    flag = true;
                }
            }
        });
        if (listSelectableTypeEditText.size() > 0
                && listSelectableTypeEditText.get(listSelectableTypeEditText.size()-1).getData().getValue().length() == 0) {
            removeSelectableTypeEditText(listSelectableTypeEditText.size() - 1);
            --currentIndexPreselectedType;
            this.addView(view);
            listSelectableTypeEditText.add(view);
            addSelectableTypeEditText(null);
        }
        else {
            this.addView(view);
            listSelectableTypeEditText.add(view);
        }
    }

    public void setListSelectableTypeEditText(List<Map.Entry<Integer, String>> listData) {
        for (SelectableTypeEditText view : listSelectableTypeEditText) {
            removeSelectableTypeEditText(view);
        }
        listSelectableTypeEditText.clear();
        if (listData != null) {
            for (Map.Entry<Integer, String> data : listData) {
                addSelectableTypeEditText(data.getValue(), data.getKey());
            }
            if (listData.size() == 0 || listData.get(listData.size()-1).getValue().length() > 0)
                addSelectableTypeEditText(null);
        }
        else
            addSelectableTypeEditText(null);

    }

    public boolean removeSelectableTypeEditText(SelectableTypeEditText view) {
        for (int i = 0; i != listSelectableTypeEditText.size(); i++) {
            if (listSelectableTypeEditText.get(i).equals(view)) {
                removeView(listSelectableTypeEditText.remove(i));
                if (i > 0)
                    listSelectableTypeEditText.get(i-1).requestFocusEndOfText();
                else if (listSelectableTypeEditText.size() > 0)
                    listSelectableTypeEditText.get(0).requestFocusEndOfText();
                return true;
            }
        }
        return false;
    }

    public boolean removeSelectableTypeEditText(int pos) {
        if (pos >= 0 && pos < listSelectableTypeEditText.size())
            return removeSelectableTypeEditText(listSelectableTypeEditText.get(pos));
        return false;
    }

    public List<Map.Entry<Integer, String>> getData() {
        List<Map.Entry<Integer, String>> listData = new LinkedList<>();

        for (SelectableTypeEditText view : listSelectableTypeEditText) {
            Map.Entry<Integer, String> data = view.getData();
            if (data.getValue().length() > 0)
                listData.add(data);
        }

        return listData;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();

        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        int count = 0;
        for (Map.Entry<Integer, String> data : getData()) {
            Bundle dataBundle = new Bundle();

            int index = -1;
            Iterator it = listSelectType.entrySet().iterator();
            int i = 0;
            while (it.hasNext()) {
                Map.Entry pairs = (Map.Entry)it.next();
                if (pairs.getKey() == data.getKey()) {
                    index = i;
                    break;
                }
                i++;
            }

            dataBundle.putInt("index", index);
            dataBundle.putString("value", data.getValue());

            bundle.putBundle("data_" + count, dataBundle);

            count++;
        }
        bundle.putInt("data_count", count);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState (Parcelable state) {
        if (state instanceof Bundle) {
            removeAllViews();
            listSelectableTypeEditText.clear();
            Bundle bundle = (Bundle) state;

            int count = bundle.getInt("data_count");
            for (int i = 0; i != count; i++) {
                Bundle dataBundle = bundle.getBundle("data_" + i);

                addSelectableTypeEditText(dataBundle.getString("value"), dataBundle.getInt("index"));
            }

            state = bundle.getParcelable("instanceState");
        }
        super.onRestoreInstanceState(state);
    }
}
