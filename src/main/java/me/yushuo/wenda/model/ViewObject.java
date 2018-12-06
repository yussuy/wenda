package me.yushuo.wenda.model;

import java.util.HashMap;
import java.util.Map;

public class ViewObject {
    Map<String, Object> objs = new HashMap<>();

    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public void get(String key) {
        objs.get(key);
    }
}
