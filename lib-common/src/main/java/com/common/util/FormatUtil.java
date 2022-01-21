package com.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 刘金豪 on 2021/1/21
 * desc: 格式化数据的工具类
 */
public class FormatUtil {
    public static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String getFormatTime(Date time){
        return sdf.format(time);
    }
}
