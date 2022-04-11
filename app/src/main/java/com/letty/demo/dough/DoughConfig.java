package com.letty.demo.dough;

/*
 *  配置类，,为实现链式API，注意建造者模式的使用
 * */

import android.content.Context;
import android.graphics.Bitmap;

import com.letty.demo.dough.cache.MemoryLruCache;
import com.letty.demo.dough.cache.MyDiskLruCache;

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
    private MemoryLruCache memoryCache;

    /**
     * 内存缓存大小,默认值
     */
    private int memoryCacheMaxSize = (int) (Runtime.getRuntime().maxMemory()) / 1024 / 8;
    /**
     * 硬盘缓存
     */
    private MyDiskLruCache disCache;

    /**
     * 硬盘缓存大小,默认值
     */
    private long diskCacheMaxSize = 1024 * 1024 * 30;


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
         * @param isCache 用户内存缓存策略,true支持内存缓存策略，false为不支持内存缓存
         * @return
         */
        public Builder setMemoryCacheEnable(Boolean isCache) {
            /*用户选择支持内存缓存*/
            if (isCache) {
                doughConfig.memoryCache = new MemoryLruCache(doughConfig.memoryCacheMaxSize);
            }
            return this;
        }

        /**
         * @param MaxSize 自定义内存缓存大小,默认支持内存缓存
         * @return
         */
        public Builder setMemoryCacheMaxSize(int MaxSize) {
            doughConfig.memoryCache = new MemoryLruCache(MaxSize);
            return this;
        }

        /**
         * @param isCache 硬盘缓存策略,true持硬盘缓存，false为不支持硬盘缓存
         * @return
         */
        public Builder setDiskCacheEnable(Boolean isCache) {
            if (isCache) {
                doughConfig.disCache = new MyDiskLruCache(doughConfig.getContext(), doughConfig.diskCacheMaxSize);
            }
            return this;
        }

        /**
         * @param maxSize 自定义硬盘缓存大小
         * @return
         */
        public Builder setDiskCacheMaxSize(long maxSize) {
            doughConfig.disCache = new MyDiskLruCache(doughConfig.context, maxSize);
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

    public MemoryLruCache getMemoryCache() {
        return memoryCache;
    }

    public MyDiskLruCache getDisCache() {
        return disCache;
    }

    public Bitmap.Config getBitmapConfig() {
        return bitmapConfig;
    }
}
