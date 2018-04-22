package com.example.latte.ui.recycler;

import java.util.LinkedHashMap;
import java.util.WeakHashMap;

/**
 * Created by liangbingtian on 2018/4/9.
 */

public class MultipleEntityBuilder {

    //内部用weakReference来引用键值对，对于不用的键值对可以快速的进行回收.
    private static final LinkedHashMap<Object, Object> FIELDS = new LinkedHashMap<>();

    public MultipleEntityBuilder() {
        //先清除之前的数据
        FIELDS.clear();
    }

    public final MultipleEntityBuilder setItemType(int itemType) {
        FIELDS.put(MultipleFields.ITEM_TYPE, itemType);
        return this;
    }

    public final MultipleEntityBuilder setField(Object key, Object value) {
        FIELDS.put(key, value);
        return this;
    }

    public final MultipleEntityBuilder setFields(WeakHashMap<?,?> map) {
        FIELDS.putAll(map);
        return this;
    }

    public final MultipleItemEntity build(){
       return new MultipleItemEntity(FIELDS);
    }
}
