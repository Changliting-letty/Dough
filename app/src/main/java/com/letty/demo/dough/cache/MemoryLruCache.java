/**
 * @TIME：2022/3/26 10:22
 * @Author:clt
 * @desc: this is for MemoryCacheLru
 * LRU内存缓存
 */

package com.letty.demo.dough.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.letty.demo.dough.loadImgs.EachRequest;
import com.letty.demo.dough.loadImgs.MD5Util;

public class MemoryLruCache {
    private LruCache<String, Bitmap> lruCache;


    /**
     * @param maxSize 用户自定义的内存缓存大小
     */
    public MemoryLruCache(Integer maxSize) {
        lruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    /**
     * 读缓存
     */
    public Bitmap get(EachRequest request) {
        String key = MD5Util.MD5(request.getUrl());
        if (key == null || key.equals("")) return null;
        return lruCache.get(key);
    }

    //写缓存

    public void put(EachRequest request, Bitmap bitmap) {
        /*以url的MD5为键*/
        String key = MD5Util.MD5(request.getUrl());
        if (key == null || key.equals("")) return;
        lruCache.put(key, bitmap);
    }

    /**
     * 移除缓存
     *
     * @param request 封装的请求对象
     */


    public void remove(EachRequest request) {
        String key = MD5Util.MD5(request.getUrl());
        if (key == null || key.equals("")) return;
        lruCache.remove(key);
    }

    /**
     * 清空缓存
     */


    public void clear() {
        lruCache.evictAll();
    }
}
