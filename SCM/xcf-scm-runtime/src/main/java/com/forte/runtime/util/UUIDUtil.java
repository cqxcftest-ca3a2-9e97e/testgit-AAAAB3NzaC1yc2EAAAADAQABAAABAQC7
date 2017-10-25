package com.forte.runtime.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by libin on 14-12-26.
 */
public class UUIDUtil {

    public static String timeBaseId(){
        StringBuilder stringBuilder=new StringBuilder();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        stringBuilder.append(sdf.format(new Date()));
        UUID uuid=UUID.randomUUID();
        stringBuilder.append(Math.abs(uuid.getMostSignificantBits()));
        stringBuilder.append(Math.abs(uuid.getLeastSignificantBits()));
        stringBuilder.setLength(24);
        return stringBuilder.toString();
    }
    
    public static String timeBaseId(int length){
        StringBuilder stringBuilder=new StringBuilder();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        stringBuilder.append(sdf.format(new Date()));
        UUID uuid=UUID.randomUUID();
        stringBuilder.append(Math.abs(uuid.getMostSignificantBits()));
        stringBuilder.append(Math.abs(uuid.getLeastSignificantBits()));
        stringBuilder.setLength(length);
        return stringBuilder.toString();
    }

    public static String shortId() {
        return timeBaseId();
    }
}
