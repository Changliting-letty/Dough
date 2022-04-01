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

import com.letty.demo.dough.Dough;
import com.letty.demo.dough.cache.AbstractCache;
import com.letty.demo.dough.log.LogUtil;
import com.letty.demo.dough.placeholder.AbstractPlaceHolderPolicy;

public abstract class AbstractLoader implements Iloader {
    private static final String mTag = "AbstractLoader"; //用于日志记录

    protected AbstractCache memoryCache = Dough.getIncetance().getConfig().getMemoryCache();
    protected AbstractCache diskCache = Dough.getIncetance().getConfig().getDisCache();
    /**
     * 占位图
     */
    protected AbstractPlaceHolderPolicy placeHolderPolicy = Dough.getIncetance().getConfig().getPlaceHolderPolicy();

    /**
     * 加载整体过程，加载管理器
     *
     * @param request
     */

    @Override
    public void load(EachRequest request) {



        /*
         * 1.读缓存
         * 2. 没有就加载
         *    显示占位符
         *    检查缓存中
         * 3.加载完成
         * 4.写入缓存
         * 5. UI线程显示
         * */
        //找内存
        Bitmap bitmap = null;
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
        //内存、硬盘中没有
        if (bitmap == null) {
            //显示加载占位图
            showLoadingImgs(request);
            //加载
            onLoad(request);
        }

    }

    /**
     * @param request 不同加载器加载
     * @return
     */
    public abstract void onLoad(EachRequest request);

    public void showLoadingImgs(EachRequest request) {
        //检查用户是否选择显示占位图
        if (placeHolderPolicy == null) return;
        final ImageView imageView = request.getImageView();
        if (imageView == null) return;
        //UI线程显示
        imageView.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageResource(placeHolderPolicy.getLoadingImg());
            }
        });
    }

    /**
     * 显示加载失败占位符
     *
     * @param request
     */
    public void showErrorImd(EachRequest request) {
        //检查用户是否选择显示占位图
        if (placeHolderPolicy == null) return;
        final ImageView imageView = request.getImageView();
        if (imageView == null) return;
        //UI线程显示
        imageView.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageResource(placeHolderPolicy.getErrorImd());
            }
        });

    }

    /**
     * UI线程展示
     *
     * @param request
     * @param bitmap
     */
    public void showInUI(final EachRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        if (imageView == null) return;
        //UI线程显示
        imageView.post(new Runnable() {
            @Override
            public void run() {
                {
                    upDateImageView(request, bitmap);
                }
            }
        });
    }

    /**
     * 图片解码，变换，再显示
     *
     * @param request
     * @param bitmap
     */
    public void upDateImageView(final EachRequest request, final Bitmap bitmap) {
        //正常加载
        ImageView imageView = request.getImageView();
        if (request != null && imageView.getTag() == request.getUrlMD5()) {
            imageView.setImageBitmap(bitmap);
        } else {
            //加载错误,错误占位图
            if (placeHolderPolicy != null) {
                imageView.setImageResource(placeHolderPolicy.getErrorImd());
            }
        }
        //加载后的一些操作
    }

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
