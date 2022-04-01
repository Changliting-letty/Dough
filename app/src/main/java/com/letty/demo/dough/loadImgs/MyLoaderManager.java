/**
 * @TIME：2022/3/30 10:24
 * @Author:clt
 * @desc: this is LoaderRegister
 * <p>
 * 加载器注册管理
 * 新加载器注册，加载器注册过后才可使用
 */

package com.letty.demo.dough.loadImgs;

import java.util.HashMap;

public class MyLoaderManager {
    private static volatile MyLoaderManager instance;
    //已经注册过的加载器
    private HashMap<String, AbstractLoader> loaderHashMap;

    private MyLoaderManager() {
        loaderHashMap = new HashMap<>();
        //默认注册好的
        regiser("https://", new UrlSourceLoader());
        regiser("http://", new UrlSourceLoader());
        regiser("file://", new FileSourceLoader());
    }

    public void regiser(String shema, AbstractLoader loader) {
        loaderHashMap.put(shema, loader);
    }

    public static MyLoaderManager getInstance() {
        if (instance == null) {
            synchronized (MyLoaderManager.class) {
                if (instance == null) {
                    instance = new MyLoaderManager();
                }
            }
        }
        return instance;
    }

    /**
     * 获取匹配的图片加载器
     *
     * @param shema
     * @return
     */
    public AbstractLoader getLoader(String shema) {
        if (shema == null || shema.equals("")) {
            return null;
        }
        return loaderHashMap.get(shema);
    }
}
