package com.pig4cloud.pigx.admin.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by ace on 2017/9/27.
 */
public class IdUtil {
    private static final LoadingCache<String,String> cahceBuilder= CacheBuilder
            .newBuilder()
            .maximumSize(1)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, String>(){
                @Override
                public String load(String key) {
                    return DateFormatUtils.format(Calendar.getInstance().getTime(),"yyyyMMdd");
                }

            });
    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };


    public static String getShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        try {
            return cahceBuilder.get("defaultkey") + shortBuffer.toString();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return shortBuffer.toString();
    }

    @Deprecated
    public static String getLongUuid() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }


}
