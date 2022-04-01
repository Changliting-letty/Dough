/**
 * @TIME：2022/3/29 15:40
 * @Author:clt
 * @desc: this is BitmapTransform
 * bitmap压缩
 * bitmap变换
 */

package com.letty.demo.dough.bitmaputil;

import android.graphics.BitmapFactory;


public class BitmapTransform {

    /**
     * 根获取压缩比例，如果原图宽比高长，则按照宽来压缩，反之则按照高来压缩
     * @param options
     * @param viewWidth  控件宽度
     * @param viewHeight
     * return : 压缩比，即原图和压缩后图的比例
     */
    public static void getSampleSizeWithOptions(BitmapFactory.Options options, int viewWidth, int viewHeight) {
        int imgWidth = options.outWidth;//图片宽度
        int imgHeight = options.outHeight; //图片高度
        int sampleSize = 1;
        if (imgWidth > imgHeight && imgWidth > viewWidth) {
            sampleSize = imgWidth / viewWidth;
        } else if (imgHeight > imgWidth && imgHeight > viewHeight) {
            sampleSize = imgHeight / viewHeight;
        }
        if (sampleSize < 1) {
            sampleSize = 1;
        }
        //保存到options
        options.inSampleSize = sampleSize;
    }

    /**
     * 图片变换，待实现
     */


}
