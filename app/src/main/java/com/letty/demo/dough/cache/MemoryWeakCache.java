/**
 * @TIME：2022/3/26 10:45
 * @Author:clt
 * @desc: 将从内存缓存中挤出的图片暂时存入 弱引用的缓存
 * <p>
 * 还没用上
 */

package com.letty.demo.dough.cache;

import android.graphics.Bitmap;

import com.letty.demo.dough.loadImgs.EachRequest;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public class MemoryWeakCache extends AbstractCache {
    private HashMap<String, BitMapWeakReferrence> weakCache;  //容器
    private ReferenceQueue<Bitmap> queue;

    private class BitMapWeakReferrence extends WeakReference<Bitmap> {
        String key;

        BitMapWeakReferrence(String key, Bitmap bitmap, ReferenceQueue<Bitmap> queue) {
            super(bitmap, queue);
            this.key = key;
        }
    }

    public MemoryWeakCache() {
        weakCache = new HashMap<>();
        queue = new ReferenceQueue<>();
    }

    //读缓存
    @Override
    public Bitmap get(EachRequest request) {
        String key = request.getUrlMD5();
        BitMapWeakReferrence bitMapWeakReferrence = weakCache.get(key);
        if (bitMapWeakReferrence == null) {
            return null;
        }
        return bitMapWeakReferrence.get();
    }

    //写
    @Override
    public void put(EachRequest request, Bitmap bitmap) {
        String key = request.getUrl();
        //清除已经被GC的
        cleanCach();
        BitMapWeakReferrence bitMapWeakReferrence = new BitMapWeakReferrence(key, bitmap, queue);  //关联
        weakCache.put(key, bitMapWeakReferrence);  //加入缓存
    }

    @Override
    public void remove(EachRequest request) {
        String key = request.getUrlMD5();
        System.out.println("do nothing");
    }
    /*清空缓存*/

    public void clear() {
        cleanCach();
        weakCache.clear();
    }

    /*
     * 清除无用引用和关系,清除已经被GC的
     * */
    private void cleanCach() {
        BitMapWeakReferrence bitMapWeakReferrence = null;
        while ((bitMapWeakReferrence = (BitMapWeakReferrence) queue.poll()) != null) {
            weakCache.remove(bitMapWeakReferrence.key);
        }
    }
}
