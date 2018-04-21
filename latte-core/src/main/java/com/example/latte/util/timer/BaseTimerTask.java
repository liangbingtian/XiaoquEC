package com.example.latte.util.timer;

import java.util.TimerTask;

/**
 * Created by liangbingtian on 2018/3/22.
 * 通用的倒计时，还需要实现一个接口在倒计时完成之后应该怎么做。
 */

public class BaseTimerTask extends TimerTask{

    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }

    @Override
    public void run() {
        //所有传入接口的方法一定要判断它是不是null
        if (mITimerListener!=null){
            mITimerListener.onTimer();
        }
    }


}
