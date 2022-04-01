/**
 * @TIME：2022/3/29 19:55
 * @Author:clt
 * @desc: this is ImageViewUtil
 * <p>
 * 获取imageView适配的宽与高
 */

package com.letty.demo.dough.loadImgs;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.lang.reflect.Field;

public class ImageViewUtil {
    public static final int DEFAULT = 200;

    /**
     * 获取高度
     * @param imageView
     * @return
     */
    public static int getImageViewWidth(ImageView imageView) {
        if (imageView != null) {
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            int width = 0;
            /*布局文件中制定了具体的宽度*/
            if (params != null && params.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
                width = imageView.getMaxWidth();
            }
            /*没有在布局文件中设置，而是在代码中通过LauoutParams指定*/
            if (width <= 0 && params != null) {
                width = params.width;
            }
            /*都没有指定，可能是对ImageView设置了maxWidth*/
//            if (width<=0 ){
//                width=getImageViewField(imageView,"mMaxWidth");
//            }
            return width > 0 ? width : DEFAULT;
        }
        return DEFAULT;
    }

    /**
     *  同理获取宽度
     * @param imageView
     * @return
     */
    public static int getImageViewHeight(ImageView imageView) {
        if (imageView != null) {
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            int height = 0;
            /*布局文件中制定了具体的宽度*/
            if (params != null && params.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
                height = imageView.getMaxHeight();
            }
            /*没有在布局文件中设置，而是在代码中通过LauoutParams指定*/
            if (height <= 0 && params != null) {
                height = params.height;
            }
            /*都没有指定，可能是对ImageView设置了maxWidth*/
//            if (height<=0 ){
//                height=getImageViewField(imageView,"mMaxHeight");
//            }
            return height > 0 ? height : DEFAULT;
        }
        return DEFAULT;
    }

    /**
     * 获取imageView的各种字段值
     * 反射实现
     */
    private static int getImageViewField(ImageView imageView, String fieldName) {
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (int) field.get(imageView);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) return fieldValue;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
