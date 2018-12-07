package me.yushuo.wenda.model;

import java.util.HashMap;
import java.util.Map;

public class ViewObject {
    private Map<String, Object> maps = new HashMap<>();

    public Map<String, Object> getMaps() {
        return maps;
    }

    public void set(String key, Object value) {
        maps.put(key, value);
    }

    public void get(String key) {
        maps.get(key);
    }
}
