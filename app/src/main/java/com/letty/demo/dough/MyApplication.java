/**
 * @TIME：2022/3/31 19:21
 * @Author:clt
 * @desc: this is MyApplication
 *
 *   获取Appliation的Context
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
    }

    public  static Context getContext(){
        return  context;
    }
}
