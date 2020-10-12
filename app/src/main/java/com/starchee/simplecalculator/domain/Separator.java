package com.starchee.simplecalculator.domain;

import java.util.HashMap;
import java.util.Map;

public enum Separator {
    DOT(".");

    private final String token;

    Separator(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    private static final Map<String, Separator> map;

    static {
        map = new HashMap<>();
        for (Separator separator : Separator.values()) {
            map.put(separator.getToken(), separator);
        }
    }

    public static boolean isSeparator(String value) {
        return map.containsKey(value);
    }
}
