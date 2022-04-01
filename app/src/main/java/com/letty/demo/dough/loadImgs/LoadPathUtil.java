/**
 * @TIME：2022/3/29 16:27
 * @Author:clt
 * @desc: this is LoadPathUtil
 * uri-> path字符串
 * aseets ->path字符串
 * R文件->path字符串
 */

package com.letty.demo.dough.loadImgs;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

public class LoadPathUtil {
    /**
     * uri->path
     *
     * @param context
     * @param uri
     * @return
     */
    @SuppressLint("Range")
    public static String getPath(Context context, Uri uri) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            return null;
        }
        if (cursor.moveToFirst()) {
            try {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return path;
    }

    /**
     * 根据图片url判断加载类型
     *
     * @param imageUrl
     * @return
     */
    public static String parseSchema(String imageUrl) {
        if (imageUrl == null || imageUrl.equals("")) {
            return null;
        }
        if (imageUrl.contains("://")) {
            //形如 http://xxx 或者file://xxx，这样截取后
            //可以获得http file等前缀，根据这个前缀获取相应
            //的加载器
            return imageUrl.split("://")[0];
        } else {
            //提示报错不支持的类型
        }
        return null;
    }

}
