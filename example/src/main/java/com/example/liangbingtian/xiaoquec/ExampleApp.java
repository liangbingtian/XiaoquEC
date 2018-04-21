package com.example.liangbingtian.xiaoquec;

import android.app.Application;

import com.example.latte.app.Latte;
import com.example.latte.ec.icon.FontEcModule;
import com.example.latte.net.interceptors.DebugInterceptor;
import com.example.latteec.database.DatabaseManager;
import com.example.latteec.icon.FontEcModule;
import com.example.liangbingtian.communityec.event.TestEvent;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by liangbingtian on 2018/3/12.
 */

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://172.20.10.8/RestServer/data/")
                .withInterceptor(new DebugInterceptor("text",R.raw.test))
                .withWeChatAppId("")
                .withWeChatAppSecret("")
                .withJavascriptInterface("latte")
                .withWebEvent("test",new TestEvent())
                .configure();
        initStetho();

        DatabaseManager.getInstance().init(this);
    }

    private void initStetho(){
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        );
    }
}
