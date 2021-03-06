package com.example.liangbingtian.xiaoquec;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.example.latte.activities.ProxyActivity;
import com.example.latte.app.Latte;
import com.example.latte.delegates.LatteDelegate;
import com.example.latte.ui.launcher.ILauncherListener;
import com.example.latte.ui.launcher.OnLauncherFinishTag;
import com.example.latteec.launcher.LauncherDelegate;
import com.example.latteec.main.EcBottomDelegate;
import com.example.latteec.main.index.IndexDelegate;
import com.example.latteec.sign.ISignListener;
import com.example.latteec.sign.SignInDelegate;
import com.example.latteec.sign.SignUpDelegate;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by liangbingtian on 2018/3/15.
 */

public class ExampleActivity extends ProxyActivity
        implements ISignListener ,ILauncherListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);
        StatusBarCompat.translucentStatusBar(this,true);

    }

    @Override
    public LatteDelegate setRootDelegate() {

        return new EcBottomDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this,"登录成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this,"注册成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag){
            case SIGNED:
//                Toast.makeText(this,"启动结束，用户登录了",Toast.LENGTH_LONG).show();
                startWithPop(new EcBottomDelegate());
                break;
            case NOT_SIGNED:
//                Toast.makeText(this,"启动结束，用户未登录",Toast.LENGTH_LONG).show();
                startWithPop(new SignInDelegate());
                break;
            default:
                break;
        }
    }
}
