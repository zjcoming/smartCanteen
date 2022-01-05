package com.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.common.constants.PermissionConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘金豪 on 2022/1/5
 * desc: 动态申请权限的工具类
 *
 * 此项目将权限分成两种，一是”必要权限“，”必要权限“中的所有权限必须都要获取，否则app无法运行。此时用requestMustPermissions方法（已在HomeFragment中实现）
 *                    二是”其他权限“，这些权限是使用某些特定功能的时候获取，比如需要扫码的时候，申请相机权限。此时用动态获取方法，即requestOtherPermissions
 *
 *
 *  申请权限，总共分为以下几步
 *      ①在app模块的Manifest文件中，对权限进行注册
 *      ②在PermissionConstants类中，对权限进行保存
 *      ③调用此类（PermissionUtil）进行权限申请。如果是必要权限，则调用requestMustPermissions
 *                                            如果是其他权限，则调用requestOtherPermissions
 */
public class PermissionUtil {
    public static void requestOtherPermissions(Context context,String permission,DialogInterface.OnClickListener secondDialogListener){
        if (context == null || permission == null){
            return;
        }
        PermissionUtil.checkPermission(context,permission,new PermissionUtil.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {
                //已经获取到
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                //被用户主动去设置中拒绝了，此时需要向用户描述下权限，然后再次请求获取权限
                //说明上次用户点击拒绝了，此时应该展示申请权限的原因，然后再去申请
                DialogUtil.selfShowTwoBtnDialog(context, "您有未申请的权限，刚刚拒绝了", PermissionConstants.requestOtherPermissionsDes + PermissionUtil.morePermissionToZh(permission),
                        "同意申请",
                        "拒绝申请",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //申请权限
                                PermissionUtil.toAppSetting(context);
                                //PermissionUtil.requestMorePermissions(context,permission,requestCode);
                            }
                        },secondDialogListener);
            }

            @Override
            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                //被用户在弹出来的权限申请中”禁止“了，此时需要进入”设置页面“
                DialogUtil.selfShowTwoBtnDialog(context, "您有未申请的权限", PermissionConstants.requestOtherPermissionsDes + PermissionUtil.morePermissionToZh(permission),
                        "同意申请",
                        "拒绝申请",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //申请权限
                                PermissionUtil.toAppSetting(context);
                                //PermissionUtil.requestMorePermissions(context,permission,requestCode);
                            }
                        },secondDialogListener);
            }
        });
    }
    /**
     * 在HomeFragment的onResume中调用的 申请”必要权限“中的所有权限。除了”必要权限“，还有一些其他的权限，他们限制某些功能的使用，可以动态请求权限
     */
    public static void requestMustPermissions(Context context) {
        if (context == null){
            return;
        }
        PermissionUtil.checkMorePermissions(context, PermissionConstants.allPermissions, new PermissionUtil.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {
                //权限已经获取了 啥也不用做
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                //说明上次用户点击拒绝了，此时应该展示申请权限的原因，然后再去申请
                DialogUtil.showOneBtnDialog(context, "您有未申请的权限，刚刚拒绝了", PermissionConstants.requestMustPermissionsDes + PermissionUtil.morePermissionToZh(permission),
                        "同意申请", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //申请权限
                                PermissionUtil.requestMorePermissions(context,permission,PermissionConstants.mustPermissionRequestCode);
                            }
                        });
            }

            @Override
            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                DialogUtil.showOneBtnDialog(context, "您有未申请的权限", PermissionConstants.requestMustPermissionsDes + PermissionUtil.morePermissionToZh(permission),
                        "同意申请", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //前往设置页面设置
                                PermissionUtil.toAppSetting(context);
                            }
                        });
            }
        });
    }
    /**
     * 权限转为汉字
     */
    public static String morePermissionToZh(String[] permission){
        String finalString = "";
        for (int i = 0;i < permission.length;i++){
            finalString += permissionToZh(permission[i].toString()) + "权限" + "\n";
        }
        return finalString;
    }
    public static String permissionToZh(String permission){
        if (permission.equals("android.permission.WRITE_EXTERNAL_STORAGE")){
            return "存储";
        }else if (permission.equals("android.permission.CAMERA")){
            return "相机";
        }

        return "";
    }
    /**
     * 检测权限
     *
     * @return true：已授权； false：未授权；
     */
    public static boolean checkPermission(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }

    /**
     * 检测多个权限
     *
     * @return 未授权的权限
     */
    public static List<String> checkMorePermissions(Context context, String[] permissions) {
        List<String> permissionList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (!checkPermission(context, permissions[i]))
                permissionList.add(permissions[i]);
        }
        return permissionList;
    }

    /**
     * 请求权限
     */
    public static void requestPermission(Context context, String permission, int requestCode) {
        ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, requestCode);
    }

    /**
     * 请求多个权限
     */
    public static void requestMorePermissions(Context context, List permissionList, int requestCode) {
        String[] permissions = (String[]) permissionList.toArray(new String[permissionList.size()]);
        requestMorePermissions(context, permissions, requestCode);
    }

    /**
     * 请求多个权限
     */
    public static void requestMorePermissions(Context context, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions((Activity) context, permissions, requestCode);
    }

    /**
     * 判断是否已拒绝过权限
     *
     * @return
     * @describe :如果应用之前请求过此权限但用户拒绝，此方法将返回 true;
     * -----------如果应用第一次请求权限或 用户在过去拒绝了权限请求，
     * -----------并在权限请求系统对话框中选择了 Don't ask again 选项，此方法将返回 false。
     */
    public static boolean judgePermission(Context context, String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission))
            return true;
        else
            return false;
    }

    /**
     * 检测权限并请求权限：如果没有权限，则请求权限
     */
    public static void checkAndRequestPermission(Context context, String permission, int requestCode) {
        if (!checkPermission(context, permission)) {
            requestPermission(context, permission, requestCode);
        }
    }

    /**
     * 检测并请求多个权限
     */
    public static void checkAndRequestMorePermissions(Context context, String[] permissions, int requestCode) {
        List<String> permissionList = checkMorePermissions(context, permissions);
        requestMorePermissions(context, permissionList, requestCode);
    }


    /**
     * 检测权限，并不申请，对检测结果的处理，由复杂回调接口决定
     */
    public static void checkPermission(Context context, String permission, PermissionCheckCallBack callBack) {
        if (checkPermission(context, permission)) { // 用户已授予权限
            callBack.onHasPermission();
        } else {
            if (!judgePermission(context, permission))  // 用户之前已拒绝过权限申请
                callBack.onUserHasAlreadyTurnedDown(permission);
            else                                       // 用户之前已拒绝并勾选了不在询问、用户第一次申请权限。
                callBack.onUserHasAlreadyTurnedDownAndDontAsk(permission);
        }
    }

    /**
     * 检测权限，并不申请，对检测结果的处理，由复杂回调接口决定
     */
    public static void checkMorePermissions(Context context, String[] permissions, PermissionCheckCallBack callBack) {
        List<String> permissionList = checkMorePermissions(context, permissions);
        if (permissionList.size() == 0) {  // 用户已授予权限
            callBack.onHasPermission();
        } else {
            boolean isFirst = true;
            for (int i = 0; i < permissionList.size(); i++) {
                String permission = permissionList.get(i);
                if (judgePermission(context, permission)) {
                    isFirst = false;
                    break;
                }
            }
            String[] unauthorizedMorePermissions = (String[]) permissionList.toArray(new String[permissionList.size()]);
            if (isFirst)// 用户之前已拒绝过权限申请
                callBack.onUserHasAlreadyTurnedDownAndDontAsk(unauthorizedMorePermissions);
            else       // 用户之前已拒绝并勾选了不在询问、用户第一次申请权限。
                callBack.onUserHasAlreadyTurnedDown(unauthorizedMorePermissions);

        }
    }


    /**
     * 检测并申请权限，对检测结果的处理，由简单回调接口决定
     */
    public static void checkAndRequestPermission(Context context, String permission, int requestCode, PermissionRequestSuccessCallBack callBack) {
        if (checkPermission(context, permission)) {// 用户已授予权限
            callBack.onHasPermission();
        } else {
            requestPermission(context, permission, requestCode);
        }
    }

    /**
     * 检测并申请多个权限，对检测结果的处理，由简单回调接口决定
     */
    public static void checkAndRequestMorePermissions(Context context, String[] permissions, int requestCode, PermissionRequestSuccessCallBack callBack) {
        List<String> permissionList = checkMorePermissions(context, permissions);
        if (permissionList.size() == 0) {  // 用户已授予权限
            callBack.onHasPermission();
        } else {
            requestMorePermissions(context, permissionList, requestCode);
        }
    }
    /**
     * 判断权限是否申请成功，用在申请权限完成后调用的方法中
     */
    public static boolean isPermissionRequestSuccess(int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }

    /**
     * 用户申请权限返回，这就是申请权限完成后调用的方法，对申请结果的处理，由复杂回调接口实现
     */
    public static void onRequestPermissionResult(Context context, String permission, int[] grantResults, PermissionCheckCallBack callback) {
        if (PermissionUtil.isPermissionRequestSuccess(grantResults)) {
            callback.onHasPermission();
        } else {
            if (PermissionUtil.judgePermission(context, permission)) {
                callback.onUserHasAlreadyTurnedDown(permission);
            } else {
                callback.onUserHasAlreadyTurnedDownAndDontAsk(permission);
            }
        }
    }

    /**
     * 用户申请多个权限返回，这就是申请权限完成后调用的方法，对申请结果的处理，由复杂回调接口实现
     */
    public static void onRequestMorePermissionsResult(Context context, String[] permissions, PermissionCheckCallBack callback) {
        boolean isBannedPermission = false;
        List<String> permissionList = checkMorePermissions(context, permissions);
        if (permissionList.size() == 0)
            callback.onHasPermission();
        else {
            for (int i = 0; i < permissionList.size(); i++) {
                if (!judgePermission(context, permissionList.get(i))) {
                    isBannedPermission = true;
                    break;
                }
            }
            //　已禁止再次询问权限
            if (isBannedPermission)
                callback.onUserHasAlreadyTurnedDownAndDontAsk(permissions);
            else // 拒绝权限
                callback.onUserHasAlreadyTurnedDown(permissions);
        }

    }


    /**
     * 跳转到权限设置界面
     */
    public static void toAppSetting(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(intent);
    }

    public interface PermissionRequestSuccessCallBack {
        /**
         * 用户已授予权限
         */
        void onHasPermission();
    }

    public interface PermissionCheckCallBack {

        /**
         * 用户已授予权限
         */
        void onHasPermission();

        /**
         * 用户已拒绝过权限
         *
         * @param permission:被拒绝的权限
         */
        void onUserHasAlreadyTurnedDown(String... permission);

        /**
         * 用户已拒绝过并且已勾选不再询问选项、用户第一次申请权限;
         *
         * @param permission:被拒绝的权限
         */
        void onUserHasAlreadyTurnedDownAndDontAsk(String... permission);
    }
}
