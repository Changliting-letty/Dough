/**
 * @TIME：2022/3/26 15:14
 * @Author:clt
 * @desc: this is DiskLruCache, 使用的第三方库 , 为保证线程安全，单例模式，
 */

package com.letty.demo.dough.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jakewharton.disklrucache.DiskLruCache;
import com.letty.demo.dough.loadImgs.EachRequest;
import com.letty.demo.dough.loadImgs.MD5Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MyDiskLruCache {
    /**
     * 默认缓存路径
     */
    private String diskImgsCache = "Image";
    private int appversion = 1;
    private int valueCount = 1;

    /**
     * 缓存目录
     */
    private File directory;
    private DiskLruCache diskLruCache;

    /**
     * 构造
     *
     * @param context
     * @param maxSize
     */
    public MyDiskLruCache(Context context, long maxSize) {
        File directory = new File(context.getCacheDir(), diskImgsCache);
        if (!directory.exists()) {
            directory.mkdir();
        }
        this.directory = directory;
        try {
            this.diskLruCache = DiskLruCache.open(directory, appversion, valueCount, maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 读缓存
     *
     * @param request 封装的请求对象
     * @return
     */

    public Bitmap get(EachRequest request) {
        String key = MD5Util.MD5(request.getUrl());
        if (diskLruCache == null) return null;
        Bitmap bitmap = null;
        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = diskLruCache.get(key);
            if (snapshot == null) return null;
            //已经是结果图，不用在压缩图片
            InputStream inputStream = (InputStream) snapshot.getInputStream(0);
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 写缓存
     *
     * @param request 封装的请求对象
     * @param bitmap  缓存的图
     */

    public void put(EachRequest request, Bitmap bitmap) {
        String key = MD5Util.MD5(request.getUrl());
        if (diskLruCache == null) return;
        try {
            DiskLruCache.Editor editor = diskLruCache.edit(key);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, editor.newOutputStream(0));
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 移除缓存
     *
     * @param request 封装的请求对象
     */

    public void remove(EachRequest request) {
        String key = MD5Util.MD5(request.getUrl());
        if (diskLruCache == null) return;
        try {
            diskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除缓存
     */

    public void clear() {
        if (diskLruCache == null) return;
        try {
            diskLruCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
