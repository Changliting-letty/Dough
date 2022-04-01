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

public class MemoryLruCache extends AbstractCache {
    private LruCache<String, Bitmap> lruCache;
    private int maxSize = (int) (Runtime.getRuntime().maxMemory() / 1024 / 8);

    /**
     * 用户选择使用内存缓存大小默认值
     */
    public MemoryLruCache() {
        //整个应用内存的的1/8
        lruCache = new LruCache<String, Bitmap>(maxSize) {
            //重写为了单位一致，返回图片占用的字节数
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    /**
     * @param maxSize 用户自定义的内存缓存大小
     */
    public MemoryLruCache(Integer maxSize) {
        this.maxSize = maxSize;
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
    @Override
    public Bitmap get(EachRequest request) {
        String key = request.getUrlMD5();
        if (key == null || key.equals("")) return null;
        return lruCache.get(key);
    }

    //写缓存
    @Override
    public void put(EachRequest request, Bitmap bitmap) {
        String key = request.getUrlMD5();
        if (key == null || key.equals("")) return;
        lruCache.put(key, bitmap);
    }

    /**
     * 移除缓存
     *
     * @param request 封装的请求对象
     */

    @Override
    public void remove(EachRequest request) {
        String key = request.getUrlMD5();
        if (key == null || key.equals("")) return;
        lruCache.remove(key);
    }

    /**
     * 清空缓存
     */

    @Override
    public void clear() {
        lruCache.evictAll();
    }
}
