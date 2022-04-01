package com.letty.demo.dough;

/*
 *  配置类，,为实现链式API，注意建造者模式的使用
 * */

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.DrawableRes;

import com.letty.demo.dough.cache.AbstractCache;
import com.letty.demo.dough.cache.MemoryLruCache;
import com.letty.demo.dough.cache.MyDiskLruCache;
import com.letty.demo.dough.loadImgs.ILoaderPolicy;
import com.letty.demo.dough.loadImgs.LoadPolicyFIFO;
import com.letty.demo.dough.loadImgs.LoadPolicyLIFO;
import com.letty.demo.dough.log.LogUtil;
import com.letty.demo.dough.placeholder.AbstractPlaceHolderPolicy;
import com.letty.demo.dough.placeholder.PlaceHolderPolicy;

public class DoughConfig {
    private static final String mTag = "DouConfig";
    private Context context;


    /**
     * bitmap解码格式
     * 默认设置为RGB565 内存消耗少一些，但是清晰度折损
     */
    private Bitmap.Config bitmapConfig;


    /**
     * 内存缓存
     */
    private AbstractCache memoryCache;

    /**
     * 硬盘缓存
     */
    private AbstractCache disCache;

    /**
     * 图片加载策略
     */
    private ILoaderPolicy loaderPolicy;

    /**
     * 占位图设置
     */
    private AbstractPlaceHolderPolicy placeHolderPolicy;

    /**
     * 默认配置
     */
    private DoughConfig() {
        bitmapConfig = Bitmap.Config.RGB_565;
    }

    /**
     * 构造者模式
     */
    public static class Builder {
        private DoughConfig doughConfig;

        public Builder(Context context) {
            doughConfig = new DoughConfig();
            doughConfig.context = context;
        }

        /**
         * @param value 用户内存缓存策略,1支持内存缓存策略，0为不支持内存缓存
         * @return
         */
        public Builder setMemoryCache(int value) {
            /*用户选择支持内存缓存*/
            if (value == Policy.MemoryCachePolicy.LRU.getValue()) {
                doughConfig.memoryCache = new MemoryLruCache();
            }
            return this;
        }

        /**
         * @param value   用户内存缓存策略,1支持内存缓存策略，0为不支持内存缓存
         * @param MaxSize 自定义内存缓存大小
         * @return
         */
        public Builder setMemoryCache(int value, int MaxSize) {
            /*用户选择支持内存缓存*/
            if (value == Policy.MemoryCachePolicy.LRU.getValue()) {
                doughConfig.memoryCache = new MemoryLruCache(MaxSize);
            }
            return this;
        }

        /**
         * @param value 硬盘缓存策略,1支持硬盘缓存，0为不支持硬盘缓存
         * @return
         */
        public Builder setDiskCache(int value) {
            if (value == Policy.DiskCachePolicy.Result.getValue()) {
                if (doughConfig.memoryCache != null) {
                    doughConfig.disCache = new MyDiskLruCache(doughConfig.getContext());
                } else {
                    LogUtil.d(mTag, "Please support memory cache first");
                }
            }
            return this;
        }

        /**
         * @param value
         * @param maxSize 自定义硬盘缓存大小
         * @return
         */
        public Builder setDiskCache(int value, int maxSize) {
            if (value == Policy.DiskCachePolicy.Result.getValue()) {
                if (doughConfig.memoryCache != null) {
                    doughConfig.disCache = new MyDiskLruCache(doughConfig.context, maxSize);
                } else {
                    LogUtil.d(mTag, "Please support memory cache first");
                }
            }
            return this;
        }

        /**
         * @param value 加载顺序  0为下FIFO  1为LIFO
         * @return
         */
        public Builder setLoaderPolicy(int value) {
            if (value == Policy.LoadPolicy.FIFO.getValue()) {
                doughConfig.loaderPolicy = new LoadPolicyFIFO();
            }
            if (value == Policy.LoadPolicy.LIFO.getValue()) {
                doughConfig.loaderPolicy = new LoadPolicyLIFO();
            }
            return this;
        }

        /**
         * 设置占位图  使用默认占位图
         *
         * @return
         */
        public Builder setPlaceholder() {
            doughConfig.placeHolderPolicy = new PlaceHolderPolicy.Builder()
                    .setLocadingImg(Policy.PlaceHolderPolicy.LOADINGIMG.getRes())
                    .setErrorImg(Policy.PlaceHolderPolicy.ErrorIMG.getRes())
                    .build();
            return this;
        }

        /**
         * 使用自定义占位图
         */
        public Builder setPlaceholder(@DrawableRes int loadingImg, @DrawableRes int errorImg) {
            doughConfig.placeHolderPolicy = new PlaceHolderPolicy.Builder()
                    .setLocadingImg(loadingImg)
                    .setErrorImg(errorImg)
                    .build();
            return this;
        }

        /**
         * 设置bitmap解码格式
         */
        public Builder setBitmapDecodeConfig(Bitmap.Config config) {
            doughConfig.bitmapConfig = config;
            return this;
        }

        public DoughConfig build() {
            return doughConfig;
        }

    }

    public Context getContext() {
        return context;
    }

    public AbstractCache getMemoryCache() {
        return memoryCache;
    }

    public AbstractCache getDisCache() {
        return disCache;
    }

    public ILoaderPolicy getLoaderPolicy() {
        return loaderPolicy;
    }

    public AbstractPlaceHolderPolicy getPlaceHolderPolicy() {
        return placeHolderPolicy;
    }

    public Bitmap.Config getBitmapConfig() {
        return bitmapConfig;
    }
}
