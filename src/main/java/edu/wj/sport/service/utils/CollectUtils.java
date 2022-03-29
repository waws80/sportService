package edu.wj.sport.service.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * 集合工具类
 */
public class CollectUtils {

    private CollectUtils(){}


    @SafeVarargs
    public static <K, V> HashMap<K, V> mapOf(Pair<K, V>... pairs){
        HashMap<K, V> map = new HashMap<>();
        for (Pair<K, V> pair : pairs) {
            map.put(pair.first, pair.second);
        }
        return map;
    }



    @SafeVarargs
    public static <V>ArrayList<V> listOf(V...values){
        ArrayList<V> list = new ArrayList<>();
        Collections.addAll(list, values);
        return list;
    }


}
