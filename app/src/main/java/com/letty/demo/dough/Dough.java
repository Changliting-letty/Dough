/**
 * @TIME：2022/3/28 16:00
 * @Author:clt
 * @desc: this is Dough
 * 接口对象
 * 单例模式
 */

package com.letty.demo.dough;

import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.letty.demo.dough.loadImgs.AsynLoad;
import com.letty.demo.dough.loadImgs.EachRequest;
import com.letty.demo.dough.placeholder.PlaceHolderPolicy;

public class Dough {
    /**
     * 配置对象的引用
     */
    private DoughConfig config;
    /**
     * 异步请求对象
     */

    private static volatile Dough instance;
    private AsynLoad asynLoad = AsynLoad.getInstance();

    private Dough(DoughConfig config) {
        this.config = config;
    }

    /**
     * 初始化实例，双重检查方式，利用配置对象进行配置，因此配置对象限制非空为空
     */
    public static void init(@NonNull DoughConfig config) {
        if (instance == null) {
            synchronized (Dough.class) {
                if (instance == null) {
                    instance = new Dough(config);
                }
            }
        }
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
     * 以下是加载图片，不同参数，重载实现
     */

    /**
     * 使用默认占位符
     */
    public void loadImgToView(ImageView view, String uri) {
        PlaceHolderPolicy placeHolderPolicy = new PlaceHolderPolicy();
        asynLoad(view, uri, placeHolderPolicy);
    }

    /**
     * 加载图片时，使用用户自定义的loadingImg,
     * 单独设置一个占位图的时候，另外一个传空
     *
     * @param imageView
     * @param uri
     * @param loadingImg
     * @param errorImg
     */
    public void loadImgToView(@NonNull ImageView imageView, @NonNull String uri, @DrawableRes Integer loadingImg, @DrawableRes Integer errorImg) {
        PlaceHolderPolicy placeHolderPolicy = new PlaceHolderPolicy(loadingImg, errorImg);
        asynLoad(imageView, uri, placeHolderPolicy);
    }

    private void asynLoad(ImageView imageView, String uri, PlaceHolderPolicy placeHolderPolicy) {
        //封装请求
        int serialNum = asynLoad.updateSerilNum(imageView.getId());
        EachRequest request = new EachRequest(uri, placeHolderPolicy, serialNum);
        asynLoad.setEachRequest(request);
        //获取imageView的宽高用于采样比的计算，避免大图内存溢出
        //显示加载占位图
        asynLoad.showLoadingImgs(imageView);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                asynLoad.setImgViewWidth(imageView.getWidth());
                asynLoad.setImgViewHeight(imageView.getHeight());

            }
        });
        asynLoad.loadAndShow(imageView);
    }

    public DoughConfig getConfig() {
        return config;
    }
}
