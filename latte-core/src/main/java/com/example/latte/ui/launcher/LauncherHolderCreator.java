package com.example.latte.ui.launcher;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * Created by liangbingtian on 2018/3/23.
 */

public class LauncherHolderCreator implements CBViewHolderCreator<LauncherHolder> {



    @Override
    public LauncherHolder createHolder() {
        return new LauncherHolder();
    }
}
