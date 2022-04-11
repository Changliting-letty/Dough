/**
 * @TIME：2022/3/31 19:21
 * @Author:clt
 * @desc: this is MyApplication
 * <p>
 * 获取Appliation的Context
 */

package com.letty.demo.dough;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        /*全局配置*/
        DoughConfig config = new DoughConfig.Builder(MyApplication.getContext())
                .setMemoryCacheEnable(true)
                .setDiskCacheEnable(true)
                .build();
        /*加载框架实例,创建以及初始化*/
        Dough.init(config);
    }

    public static Context getContext() {
        return context;
    }
}
