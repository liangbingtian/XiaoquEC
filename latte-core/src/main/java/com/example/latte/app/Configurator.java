package com.example.latte.app;

import android.app.Activity;
import android.os.Handler;

import com.example.latte.delegates.web.event.Event;
import com.example.latte.delegates.web.event.EventManager;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by liangbingtian on 2018/3/12.
 * 用于配置文件的存储及其获取
 */

public class Configurator {


    private static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap<>();
    private static final Handler HANDLER = new Handler();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();


    private Configurator() {
        //配置开始并没有配置完成
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY.name(), false);
        LATTE_CONFIGS.put(ConfigKeys.HANDLER.name(), HANDLER);
    }

    private static class Holder {

        private static final Configurator INSTANCE = new Configurator();
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    final HashMap<Object, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }

    public final Configurator withHandler(Handler handler) {
        LATTE_CONFIGS.put(ConfigKeys.HANDLER.name(), handler);
        return this;
    }

    public final void configure() {
        initIcons();
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY.name(), true);
    }

    public final Configurator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigKeys.API_HOST.name(), host);
        return this;
    }

    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcon(IconFontDescriptor descriptor) {

        ICONS.add(descriptor);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {

        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;

    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {

        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;

    }

    public final Configurator withWeChatAppId(String appId) {

        LATTE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_ID, appId);
        return this;
    }

    public final Configurator withWeChatAppSecret(String appSecret) {

        LATTE_CONFIGS.put(ConfigKeys.WE_CHAT_APP_SECRET, appSecret);
        return this;
    }

    public final Configurator withActivity(Activity activity) {

        LATTE_CONFIGS.put(ConfigKeys.ACTIVITY, activity);
        return this;
    }

//    public Configurator withJavascriptInterface(@NotNull String name) {
//        LATTE_CONFIGS.put(ConfigKeys.JAVASCRIPT_INTERFACE, name);
//        return this;
//    }
//
//    public Configurator withWebEvent(@NotNull String name, @NotNull Event event) {
//        final EventManager manager = EventManager.getInstance();
//        manager.addEvent(name, event);
//        return this;
//    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = LATTE_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) LATTE_CONFIGS.get(key);
    }
}
