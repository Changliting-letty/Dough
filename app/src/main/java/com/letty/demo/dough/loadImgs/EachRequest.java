/**
 * @TIME：2022/3/27 8:43
 * @Author:clt
 * @desc: 封装每一个图片加载请求
 */

package com.letty.demo.dough.loadImgs;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.letty.demo.dough.Dough;
import com.letty.demo.dough.cache.AbstractCache;
import com.letty.demo.dough.placeholder.AbstractPlaceHolderPolicy;

import java.lang.ref.SoftReference;

public class EachRequest implements Comparable<EachRequest> {
    /**
     * 图片下载地址
     */
    private String url;
    /**
     * 加密后的图片下载路径，缓存键
     */
    private String urlMD5;

    /**
     * 显示配置
     */

    private AbstractPlaceHolderPolicy placeHolderPolicy;
    /**
     * 加载顺序配置
     */

    private ILoaderPolicy loaderPolicy = Dough.getIncetance().getConfig().getLoaderPolicy();

    /**
     * 图片顺序号，用于图片加载顺序
     */
    private int serialNum;

    /**
     * imgview的软引用？？？
     */
    private SoftReference<ImageView> imageViewSoftReference;

    /**
     * 下载完成的监听  ？？ 需要吗
     */


    /**
     * 构造
     */
    public EachRequest() {
    }

    public EachRequest(ImageView imageView, String url) {
        this(imageView, url, null);
    }

    public EachRequest(ImageView imageView, String url, AbstractPlaceHolderPolicy placeHolderPolicy) {
        this.imageViewSoftReference = new SoftReference<ImageView>(imageView);

        if (url != null) {
            imageView.setTag(url);
            this.urlMD5 = MD5Util.MD5(url);
        }
        this.url = url;
        if (placeHolderPolicy != null) {
            this.placeHolderPolicy = placeHolderPolicy;
        }
    }

    /**
     * 重写比较方法, 不同加载策略
     */
    @Override
    public int compareTo(EachRequest o) {
        return loaderPolicy.comparePriority(o, this);
    }

    public int getSerialNum() {
        return serialNum;
    }

    public ImageView getImageView() {
        if (imageViewSoftReference == null) return null;
        return imageViewSoftReference.get();
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

    public String getUrlMD5() {
        return urlMD5;
    }

    public void setUrlMD5(String urlMD5) {
        this.urlMD5 = urlMD5;
    }

    /**
     * 涉及eachRequest对象的比较，所需重写下列韩式
     */
    @Override
    public int hashCode() {
        int res = loaderPolicy != null ? loaderPolicy.hashCode() : 0;
        int tem = loaderPolicy.hashCode();
        while ((tem >> 1) > 0) {
            res = res * 88 + serialNum;
        }

        return res;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj instanceof EachRequest) {
            EachRequest eachRequest = (EachRequest) obj;
            if (serialNum == eachRequest.serialNum && loaderPolicy == eachRequest.loaderPolicy) {
                return true;
            }
        }
        return false;
    }

}
