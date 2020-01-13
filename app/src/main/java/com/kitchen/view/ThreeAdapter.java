package com.kitchen.view;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.kitchen.activity.R;
import com.kitchen.utils.Control;
import com.nightonke.jellytogglebutton.JellyToggleButton;
import com.nightonke.jellytogglebutton.State;

import java.util.List;
import java.util.Map;

import cn.iwgang.countdownview.CountdownView;
import cn.iwgang.countdownview.DynamicConfig;

/**
 * Author:jankin
 * Time:19-9-3 上午10:35
 * Description: This is ThreeAdapter
 */

public class ThreeAdapter extends BaseAdapter {

    private static final String TAG = "ThreeAdapter";
    List<Map<String, Object>> list;
    LayoutInflater layoutInflater;
    private final String GREEN = "#00FF09";
    private final String RED = "#f44336";
    private Map auxMap;

    public void setControl(Control control) {
        this.control = control;
    }

    Control control;

    public ThreeAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    public ThreeAdapter(List<Map<String, Object>> list) {
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
        View view = layoutInflater.inflate(R.layout.item, null);
        ImageView logo = view.findViewById(R.id.logo);
        TextView title = view.findViewById(R.id.txv_equipment_type);
        TextView note = view.findViewById(R.id.txv_equipment_name);
        JellyToggleButton jellyToggleButton = view.findViewById(R.id.jtb_21);
        CountdownView countdownView = view.findViewById(R.id.cv_countdownView);


        jellyToggleButton.setText("OFF","ON");
        jellyToggleButton.setTextColor(Color.parseColor("#FF000000"));
        jellyToggleButton.setTextSize(20);
        auxMap = list.get(position);
        logo.setImageResource((Integer) auxMap.get("logo"));
        title.setText((String) auxMap.get("title"));
        note.setText((String) auxMap.get("note"));
        jellyToggleButton.setChecked((Boolean) auxMap.get("state"));
        initListener(position, jellyToggleButton, countdownView);
        return view;
    }


    /*
     *@Params:[position, jellyToggleButton, countdownView]
     *@Author:jankin
     *@Date:19-9-7
     */
    private void initListener(final int position, final JellyToggleButton jellyToggleButton, final CountdownView countdownView) {

        jellyToggleButton.setOnStateChangeListener(new JellyToggleButton.OnStateChangeListener() {

            @Override
            public void onStateChange(float process, State state, JellyToggleButton jtb) {
                if (state.equals(State.LEFT)) {
                    Log.i(TAG, "State.LEFT: "+position);
                    setColor(countdownView,GREEN);
                    list.get(position).put("state",false);
                }
                if (state.equals(State.RIGHT)) {
                    Log.i(TAG, "State.RIGHT: "+position);
                    setColor(countdownView,RED);
                    list.get(position).put("state",true);
                }
            }
        });
        countdownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: countdownView" + position);
                control.btnTimePickerDialog(position,countdownView);
            }
        });
        countdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
                Log.i(TAG, "onEnd: "+position);
                Log.i(TAG, "onEnd: "+jellyToggleButton.isChecked());
                if(jellyToggleButton.isChecked()){
                    jellyToggleButton.setChecked(false);
                }else{
                    jellyToggleButton.setChecked(true);
                }
            }
        });
    }

    /*
     *@Params:[countdownView, color]
     *@Author:jankin
     *@Date:19-9-7
     */
    private void setColor(CountdownView countdownView, String color) {
        DynamicConfig.Builder dynamicConfigBuilder = new DynamicConfig.Builder();
        DynamicConfig.BackgroundInfo backgroundInfo = new DynamicConfig.BackgroundInfo();
        backgroundInfo.setColor(Color.parseColor(color))
                .setShowTimeBgDivisionLine(true);
        dynamicConfigBuilder
                .setTimeTextColor(0xFF000000)
                .setSuffixTextColor(0xFF000000)
                .setShowMillisecond(false)
                .setShowHour(true)
                .setBackgroundInfo(backgroundInfo);
        countdownView.dynamicShow(dynamicConfigBuilder.build());
    }
}
