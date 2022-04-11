/**
 * @TIME：2022/3/28 15:08
 * @Author:clt
 * @desc: this is DisplayPolicy
 * <p>
 * 建造者模式设置不同的占位图
 */

package com.letty.demo.dough.placeholder;

import androidx.annotation.DrawableRes;

import com.letty.demo.dough.R;

public class PlaceHolderPolicy {

    private int loadingImg;
    private int errorImg;

    /**
     * 使用默认占位图
     */
    public PlaceHolderPolicy() {
        this.loadingImg = R.drawable.load;
        this.errorImg = R.drawable.error;
    }

    /**
     * 自定义占位符
     */
    public PlaceHolderPolicy(@DrawableRes Integer loadingImg, @DrawableRes Integer errorImg) {
        if (loadingImg != null) {
            this.loadingImg = loadingImg;
        } else {
            this.loadingImg = R.drawable.load;
        }
        if (errorImg != null) {
            this.errorImg = errorImg;
        } else {
            this.errorImg = R.drawable.error;
        }
    }

    /**
     * 获取加载中占位图
     *
     * @return
     */

    public int getLoadingImg() {
        return loadingImg;
    }

    /**
     * 获取错误占位图
     *
     * @return
     */

    public int getErrorImg() {
        return errorImg;
    }


}
