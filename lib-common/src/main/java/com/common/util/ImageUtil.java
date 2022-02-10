package com.common.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.base.UIUtils;
import com.common.constants.BaseUserInfo;
import com.common.repository.UserRepository;
import com.common.requestbase.AppObserver;
import com.common.requestbase.ResponseModel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import io.reactivex.annotations.NonNull;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 图片加载类 可用来保存图片到本地，加载本地图片
 */
public class ImageUtil {
    /**
     * 保存图片到本地
     * @param photoBitmap 图片的Bitmap类型
     * @param imgName 图片的名字
     *
     * 保存到本地的路径为：Environment.getExternalStorageDirectory() + "/smart_canteen/photo/{imgName}.jpg
     */
    public static void savePhotoToStorage(Bitmap photoBitmap, String imgName) {
        //更改的名字
        String photoName = imgName + ".jpg";
        String photoPath = Environment.getExternalStorageDirectory() + "/smart_canteen/photo";

        File fileDir = new File(photoPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        FileOutputStream fos = null;
        File photoFile = null;
        try {
            //重命名并保存
            photoFile = new File(fileDir, photoName);
            photoFile.createNewFile();
            fos = new FileOutputStream(photoFile);
            photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 保存头像到服务器
     * @param imgName 图片名字 最好有自己的规定 比如userID + imgName 组成 imgName
     */
    public static void savePhotoToServer(String imgName){
        //图片路径
        String photoName = imgName + ".jpg";
        String photoPath = Environment.getExternalStorageDirectory() + "/smart_canteen/photo";

        //得到图片文件
        File fileDir = new File(photoPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File photoFile = new File(fileDir, photoName);

        //如果图片不存在，则返回
        if (!photoFile.exists()){
            UIUtils.INSTANCE.showToast("图片不存在");
            return;
        }

        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpeg");
        RequestBody filebody = MultipartBody.create(MEDIA_TYPE_PNG, photoFile);
        MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
        //这里是 封装上传图片参数
        multiBuilder.addFormDataPart("profilePhotoFile", photoFile.getName(), filebody);
        //参数以添加header方式将参数封装，否则上传参数为空
        // 设置请求体
        multiBuilder.setType(MultipartBody.FORM);
        //这里是 封装上传图片参数
        //multiBuilder.addFormDataPart("file", photoFile.getName(), filebody);
        // 封装请求参数,这里最重要
        HashMap<String, String> params = new HashMap<>();
        params.put("uid",BaseUserInfo.getUserBean().getUid());
        //参数以添加header方式将参数封装，否则上传参数为空
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                multiBuilder.addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, params.get(key)));
            }
        }
        RequestBody multiBody = multiBuilder.build();
        //请求服务器
        UserRepository.getUserRepository().setUserIconToServer(new AppObserver<ResponseModel<HashMap<String,String>>>() {
            @Override
            public void onData(@NonNull ResponseModel<HashMap<String,String>> o) {
                //设置成功了
                UIUtils.INSTANCE.showToast("图片已保存到服务器");
                if (o == null || o.getData() == null){
                    return;
                }
                //如果是用户的头像，则需要保存到BaseUserInfo类
                BaseUserInfo.getUserBean().setProfilePhoto(o.getData().get("profilePhoto"));
            }
        }, multiBody);
    }

    /**
     *  从本地得到图片
     * @param imgName 图片名称
     * @return
     */
    public static Bitmap getPhotoFromStorage(String imgName) {
        String photoPath = android.os.Environment.getExternalStorageDirectory() + "/smart_canteen/photo/" + imgName + ".jpg";
        return getBitmapFromPath(photoPath, 80, 80);
    }
    // 从路径获取Bitmap
    public static Bitmap getBitmapFromPath(String imgPath, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, options);

        // Calculate inSampleSize
        //options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        //避免出现内存溢出的情况，进行相应的属性设置。
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;

        return BitmapFactory.decodeFile(imgPath, options);
    }

    public static int calculateInSampleSize( //参2和3为ImageView期待的图片大小
                                             BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 图片的实际大小
        final int height = options.outHeight;
        final int width = options.outWidth;
        //默认值
        int inSampleSize = 1;
        //动态计算inSampleSize的值
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height/2;
            final int halfWidth = width/2;
            while( (halfHeight/inSampleSize) >= reqHeight && (halfWidth/inSampleSize) >= reqWidth){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }



    public static Bitmap getBitmapFromByte(byte[] imgByte, int reqWidth, int reqHeight) {
        InputStream input = null;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        input = new ByteArrayInputStream(imgByte);
        SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
                input, null, options));
        bitmap = (Bitmap) softRef.get();
        if (imgByte != null) {
            imgByte = null;
        }

        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}


