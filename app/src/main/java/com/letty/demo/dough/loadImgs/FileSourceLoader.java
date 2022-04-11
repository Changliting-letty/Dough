/**
 * @TIME：2022/3/28 15:42
 * @Author:clt
 * @desc: this is LocalLoader
 * 本地图片加载
 * 图片来源：本地文件
 * file://开头
 */

package com.letty.demo.dough.loadImgs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.letty.demo.dough.Dough;
import com.letty.demo.dough.bitmaputil.BitmapTransform;
import com.letty.demo.dough.log.LogUtil;

import java.io.File;

public class FileSourceLoader extends AbstractLoader {
    private static final String mTag = "FileSourceLoader";

    /**
     * 未考虑大图内存溢出的问题
     *
     * @param request 不同加载器加载
     * @return
     */
    @Override
    public Bitmap onLoad(EachRequest request) {
        Bitmap bitmap = null;
        //文件路径
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只对宽高，不全部读图到内存
        options.inPreferredConfig = Dough.getIncetance().getConfig().getBitmapConfig();
        final String filePath = Uri.parse(request.getUrl()).getPath();
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            LogUtil.d(mTag, "The loading file doesn't exist.");
            return null;
        }
        bitmap = BitmapFactory.decodeFile(filePath, options); //options中含有图片大小
        //获取采样比
        int imageviewWidth = request.getImgViewWidth();
        int imageviewHeight = request.getImgViewHeight();
        BitmapTransform.getSampleSizeWithOptions(options, imageviewWidth, imageviewHeight);
        options.inJustDecodeBounds = false;//复位
        bitmap = BitmapFactory.decodeFile(filePath, options);
        return bitmap;
    }
}
