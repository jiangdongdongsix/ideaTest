package com.iqes.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatTool {

    public static String getCurrentTime(){
        SimpleDateFormat sim21  = new SimpleDateFormat("yyyyMMddHHmmss");
        return sim21.format(new Date());
    }

}
