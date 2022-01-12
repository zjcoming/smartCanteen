package com.common.util;

import android.content.Context;

import com.base.ApplicationContext;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 这是MMKV的封装类
 *
         使用介绍
         ①基本使用
         存储数据： MMKVUtil.getMMKV(getContext()).encode("key",value);
         解析数据： MMKVUtil.getMMKV(getContext()).decodeXXX("key");

         ②得到数据存储目录
         MMKVUtil.getMMKV(getContext());

 */
public class MMKVUtil {
    //使用Map来保存不同context下的MMKV实例
    private static Map mmkvMap = new HashMap<Context,MMKV>();

    public static MMKV getMMKV(){
        return getMMKV(ApplicationContext.getContext());
    }
    //得到MMKV实例
    public static MMKV getMMKV(Context mContext){
        if(mmkvMap.get(mContext) == null){
            //说明还没创建此context下的MMKV
            MMKV.initialize(mContext);
            MMKV kv = MMKV.defaultMMKV();

            mmkvMap.put(mContext,kv);
            return kv;
        }else {
            return (MMKV)mmkvMap.get(mContext);
        }
    }

    //得到数据存储位置
    public static String getDataLocation(Context mContext){
        String rootDir = MMKV.initialize(mContext);
        return rootDir;
    }
}