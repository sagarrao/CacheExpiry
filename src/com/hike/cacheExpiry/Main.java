package com.hike.cacheExpiry;

import com.hike.cacheExpiry.impl.ExpiryCacheImpl;

import java.time.temporal.ChronoUnit;

/**
 * Created by RaoSa on 12/3/2015.
 */
public class Main {

    public static void main(String[] args) {
        ExpiryCacheImpl cache = new ExpiryCacheImpl();
        String key1 = "key1";
        String key2 = "key2";
        String key3 = "key3";
        String key4 = "key4";
        String key5 = "key5";
        String key6 = "key6";
        Integer one = new Integer(1);
        Integer two = new Integer(2);
        cache.put(key3, two, 460, ChronoUnit.MILLIS);
        cache.put(key1, one, 3000, ChronoUnit.MILLIS);
        cache.put(key2, two, 360, ChronoUnit.MILLIS);


        System.out.println("key1 before sleep-->" + cache.get(key1));
        System.out.println("key2 before sleep-->" + cache.get(key2));
        System.out.println("key3 after sleep-->" + cache.get(key3));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("key1 after sleep-->" + cache.get(key1));
        System.out.println("key2 after sleep-->"+cache.get(key2));
        System.out.println("key3 after sleep-->" + cache.get(key3));

    }
}
