/**
 * @TIME：2022/3/27 8:43
 * @Author:clt
 * @desc: 封装每一个图片加载请求
 */

package com.letty.demo.dough.loadImgs;

import android.graphics.Bitmap;
import com.letty.demo.dough.placeholder.PlaceHolderPolicy;



public class EachRequest {
    /**
     * 图片下载地址
     */
    private String url;


    /**
     * 占位图设置
     */

    private PlaceHolderPolicy placeHolderPolicy;
    /**
     * 最后加载的图片
     */
    private Bitmap bitmap;
    /**
     * 图片顺序号，确保显示的是最后加载的
     */
    private int serialNum;

    /**
     * imageView的宽高，用于获取采样比
     */
    private int imgViewWidth;
    private int imgViewHeight;

    public EachRequest(String url, int serialNum) {
        this.url = url;
        this.placeHolderPolicy = new PlaceHolderPolicy();
        this.serialNum = serialNum;
    }


    public EachRequest(String url, PlaceHolderPolicy placeHolderPolicy, int serialNum) {
        this.url = url;
        if (placeHolderPolicy != null) {
            this.placeHolderPolicy = placeHolderPolicy;
        }
        this.serialNum = serialNum;
    }


    public PlaceHolderPolicy getPlaceHolderPolicy() {
        return placeHolderPolicy;
    }

    public int getSerialNum() {
        return serialNum;
    }


    public void setSerialNum(int serialNum) {
        this.serialNum = serialNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImgViewWidth(int imgViewWidth) {
        this.imgViewWidth = imgViewWidth;
    }

    public void setImgViewHeight(int imgViewHeight) {
        this.imgViewHeight = imgViewHeight;
    }

    public int getImgViewWidth() {
        return imgViewWidth;
    }

    public int getImgViewHeight() {
        return imgViewHeight;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
