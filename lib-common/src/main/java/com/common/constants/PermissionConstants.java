package com.common.constants;

import android.Manifest;

/**
 * Created by 刘金豪 on 2021/1/5
 * desc: 记录所有的权限
 */
public class PermissionConstants {
    /**
     * 下面放整个app需要的所有权限
     */
    public static final String internet = Manifest.permission.INTERNET;//此权限默认就有
    public static final String write_external_storage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String camera = Manifest.permission.CAMERA;

    /**
     * 用数组保存所有的权限
     */
    public static final String[] allPermissions = {
            write_external_storage,
            camera
    };

    /**
     * 权限申请的code
     */
    public static final int mustPermissionRequestCode = 1;
    public static final int otherPermissionRequestCode = 2;

    /**
     * 权限申请描述
     */
    public static final String requestMustPermissionsDes = "同意以下权限后才能使用app\n\n";
    public static final String requestOtherPermissionsDes = "同意以下权限后才能使用此功能\n\n";
}
