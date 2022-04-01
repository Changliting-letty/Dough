package com.letty.demo.dough.cache;

/*
 缓存操作的接口： 读缓存、写缓存、删除缓存
*这里选取抽象类和接口的依据：
*   不需要什么公共属性
*   也不需要一些具体实现
*   并且以后的实现类可能还需要继承其他类
*   基本功能以后变化不大，所以用接口也无防
**/

import android.graphics.Bitmap;

import com.letty.demo.dough.loadImgs.EachRequest;

import java.io.IOException;

public abstract class AbstractCache {


    /**
     * @param request 封装的请求对象
     * @return
     */
    public abstract Bitmap get(EachRequest request);

    /**
     * @param request 封装的请求对象
     * @param bitmap  缓存的图
     */
    public abstract void put(EachRequest request, Bitmap bitmap);


    /**
     * @param request 封装的请求对象
     */
    public abstract void remove(EachRequest request);

    /**
     * 清空缓存
     */
    public abstract void clear();

}
