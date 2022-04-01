/**
 * @TIME：2022/3/28 16:00
 * @Author:clt
 * @desc: this is Dough
 * 接口对象
 * 单例模式
 */

package com.letty.demo.dough;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.letty.demo.dough.loadImgs.AsynTaskLoad;
import com.letty.demo.dough.loadImgs.EachRequest;


public class Dough {
    /**
     * 配置对象的引用
     */
    private DoughConfig config;
    /**
     * 异步请求对象
     */

    private static volatile Dough instance;

    private Dough(DoughConfig config) {
        this.config = config;

    }

    /**
     * 双重检查方式，利用配置对象进行配置，因此配置对象限制非空为空
     */
    public static Dough init(@NonNull DoughConfig config) {
        if (instance == null) {
            synchronized (Dough.class) {
                if (instance == null) {
                    instance = new Dough(config);
                }
            }
        }
        return instance;   //可用于链式API
    }

    /**
     * 内部用获取单例对象中的资源，比如缓存对象、加载器对象
     */
    public static Dough getIncetance() {
        if (instance == null) {
            throw new UnsupportedOperationException("the singleton of Dough have not been initialed.");
        }
        return instance;
    }

    /**
     * 加载图片API， 需要URI绝对路径
     */
    public void loadImgToView(ImageView view, String uri) {
        EachRequest request = new EachRequest(view, uri, config.getPlaceHolderPolicy());
        AsynTaskLoad asynTaskLoad = new AsynTaskLoad();
        asynTaskLoad.execute(request);
    }

    public DoughConfig getConfig() {
        return config;
    }
}
