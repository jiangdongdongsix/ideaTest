package com.iqes.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式处理工具类
 */
public class TimeFormatTool {

    private static  final  SimpleDateFormat SIM = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     *  按规定的格式返回当前时间
     * @return
     */
    public static String getCurrentTime(){
        return SIM.format(new Date());
    }

    /**
     * 传入一个时间字符串与系统当前时间做比较
     * 单位：毫秒
     * @param date
     * @return long
     * @throws Exception
     */
    public static Long diffTime(String date) throws Exception{
        System.out.println("date="+ date);
        long diffTime = System.currentTimeMillis() - SIM.parse(date).getTime();
        if(diffTime < 0){
            return 0L;
        }
        return diffTime;
    }


}
