package com.kitchen.utils;


import com.nightonke.jellytogglebutton.State;

import cn.iwgang.countdownview.CountdownView;

public interface Control {
        void btnTimePickerDialog(int position, CountdownView countdownView);
        void btnJellyToggleButton(int position, State state);
}
