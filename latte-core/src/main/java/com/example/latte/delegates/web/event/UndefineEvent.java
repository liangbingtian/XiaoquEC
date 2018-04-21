package com.example.latte.delegates.web.event;

import android.util.Log;

/**
 * Created by liangbingtian on 2018/4/18.
 */

public class UndefineEvent extends Event {
    @Override
    public String execute(String params) {
        Log.e("UndefindedEvent", params);
        return null;
    }
}
