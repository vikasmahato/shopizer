package com.salesmanager.shop.store.controller;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UrlToGroupMapping {

    private UrlToGroupMapping() {
    }

    private static ImmutableMap<String, String> uriToGroupMap = ImmutableMap.of(
            "safety-shoe-ppc", "SAFETY_SHOES",
            "default", "FEATURED_ITEM"
    );

    private static List<String> keys = new ArrayList<String>(uriToGroupMap.keySet());

    public static String getGroupForUri(String uri) {
        for(String key: keys){
            if(uri.toLowerCase(Locale.ROOT).contains(key))
                return uriToGroupMap.get(key);
        }

        return uriToGroupMap.get("default");
    }
}
