/**
 * @TIME：2022/3/28 15:04
 * @Author:clt
 * @desc: 占位图
 */

package com.letty.demo.dough.placeholder;

import androidx.annotation.DrawableRes;

public abstract class AbstractPlaceHolderPolicy {


    /**
     * 图片加载中 占位图
     * @return
     */
    public abstract @DrawableRes
    int getLoadingImg();

    /**
     * 图片加载失败占位图
     * @return
     */
    public abstract @DrawableRes
    int getErrorImd();
}
