package com.kitchen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kitchen.activity.R;

import java.util.List;
import java.util.Map;

/**
 * Author:jankin
 * Time:19-9-3 上午10:35
 * Description: This is FiveAdapter
 */

public class FiveAdapter extends BaseAdapter {

    private static final String TAG = "ThreeAdapter";
    List<Map<String, Object>> list;
    LayoutInflater layoutInflater;
    private final String GREEN = "#00FF09";
    private final String RED = "#f44336";
    private Map auxMap;



    public FiveAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    public FiveAdapter(List<Map<String, Object>> list) {
        this.list = list;
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.four_item, null);
        ImageView logo = view.findViewById(R.id.logo);
        TextView equipmentType = view.findViewById(R.id.txv_equipment_type);
        TextView equipmentName = view.findViewById(R.id.txv_equipment_name);
        TextView equipmentLimitTime = view.findViewById(R.id.txv_equipment_limit_time);
        TextView equipmentTime = view.findViewById(R.id.txv_equipment_time);

        auxMap = list.get(position);
        logo.setImageResource((Integer) auxMap.get("logo"));
        equipmentType.setText((String) auxMap.get("equipmentType"));
        equipmentName.setText((String) auxMap.get("equipmentName"));
        equipmentLimitTime.setText((Integer) auxMap.get("equipmentLimitTime") + "");
        equipmentTime.setText((String) auxMap.get("equipmentTime"));
        return view;
    }
}
