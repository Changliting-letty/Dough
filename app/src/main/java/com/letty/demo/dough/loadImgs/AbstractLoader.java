/**
 * @TIME：2022/3/27 9:27
 * @Author:clt
 * @desc: this is AbstractLoader
 * 不同策略不同加载器：本地加载 or 网络加载
 * 不同加载器的相同操作：
 * 加载管理器
 * 缓存操作
 * 加载图片
 * 占位图设置
 * 加载后的显示回调
 * 这里体现依赖倒置原则
 */

package com.letty.demo.dough.loadImgs;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.letty.demo.dough.Dough;
import com.letty.demo.dough.cache.MemoryLruCache;
import com.letty.demo.dough.cache.MyDiskLruCache;
import com.letty.demo.dough.log.LogUtil;
import com.letty.demo.dough.placeholder.PlaceHolderPolicy;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class AbstractLoader {
    private static final String mTag = "AbstractLoader"; //用于日志记录
    protected MemoryLruCache memoryCache = Dough.getIncetance().getConfig().getMemoryCache();
    protected MyDiskLruCache diskCache = Dough.getIncetance().getConfig().getDisCache();


    public Observable<EachRequest> getImage(EachRequest request) {
        return Observable.create((ObservableOnSubscribe<EachRequest>) e -> {
            if (!e.isDisposed()) {
                Bitmap bitmap = null;
                //找内存
                if (memoryCache != null) {
                    bitmap = memoryCache.get(request);
                    //找硬盘
                    if (bitmap == null) {
                        if (diskCache != null) {
                            bitmap = diskCache.get(request);
                        }
                        if (bitmap != null) {
                            //写入缓存
                            putToMemoryCache(request, bitmap);
                        }
                    }
                }
                //不在缓存，使用加载器加载
                if (bitmap == null) {
                    bitmap = onLoad(request);  //不同加载器实现
//               写入内存缓存
                    if (bitmap != null) {
                        putToMemoryCache(request, bitmap);
                    }
                }
                request.setBitmap(bitmap);
                e.onNext(request);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 加载整体过程，加载管理器
     *
     * @param request
     */


    /**
     * @param request 不同加载器加载
     * @return
     */
    public abstract Bitmap onLoad(EachRequest request);


    /**
     * 存入内存缓存
     *
     * @param request
     * @param bitmap
     */
    public void putToMemoryCache(EachRequest request, Bitmap bitmap) {
        if (memoryCache != null) {
            if (request != null && bitmap != null) {
                synchronized (AbstractLoader.class) {
                    memoryCache.put(request, bitmap);
                }
            }
        }
    }

    /**
     * 存入硬盘缓存
     *
     * @param request
     * @param bitmap
     */
    public void putToDiskCache(EachRequest request, Bitmap bitmap) {
        if (diskCache != null) {
            if (request != null && bitmap != null) {
                synchronized (AbstractLoader.class) {
                    diskCache.put(request, bitmap);
                }
            }
        }
    }


}
