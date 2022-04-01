/**
 * @TIME：2022/3/28 15:08
 * @Author:clt
 * @desc: this is DisplayPolicy
 * <p>
 * 建造者模式设置不同的占位图
 */

package com.letty.demo.dough.placeholder;

import androidx.annotation.DrawableRes;

import com.letty.demo.dough.Policy;

public class PlaceHolderPolicy extends AbstractPlaceHolderPolicy {

    private int loadingImg;
    private int errorImg;

    /**
     * 获取加载中占位图
     *
     * @return
     */
    @Override
    public int getLoadingImg() {
        return loadingImg;
    }

    /**
     * 获取错误占位图
     *
     * @return
     */
    @Override
    public int getErrorImd() {
        return errorImg;
    }

    public static class Builder {
        PlaceHolderPolicy placeHolderPolicy;

        public Builder() {
            placeHolderPolicy = new PlaceHolderPolicy();
        }

        public Builder setLocadingImg(@DrawableRes int locadingImg) {
            placeHolderPolicy.loadingImg = locadingImg;
            return this;
        }

        public Builder setErrorImg(@DrawableRes int errorImg) {
            placeHolderPolicy.errorImg = errorImg;
            return this;
        }

        public PlaceHolderPolicy build() {
            return placeHolderPolicy;

        }
    }
}
