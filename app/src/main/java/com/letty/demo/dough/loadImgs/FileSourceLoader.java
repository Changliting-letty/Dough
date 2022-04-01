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

import java.io.File;

public class FileSourceLoader extends AbstractLoader {
    private static final String mTag = "FileSourceLoader";

    @Override
    public void onLoad(EachRequest request) {
        //控件宽高
        ImageView imageView = request.getImageView();
        int imageviewWidth = ImageViewUtil.getImageViewWidth(imageView);
        int imageviewHeight = ImageViewUtil.getImageViewHeight(imageView);
        //文件路径
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只对宽高，不全部读图到内存
        options.inPreferredConfig = Dough.getIncetance().getConfig().getBitmapConfig();
        final String filePath = Uri.parse(request.getUrl()).getPath();
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) return;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options); //options中含有图片大小
        //获取采样比
        BitmapTransform.getSampleSizeWithOptions(options, imageviewWidth, imageviewHeight);
        options.inJustDecodeBounds = false;//复位
        bitmap = BitmapFactory.decodeFile(filePath, options);
        if (bitmap == null) {
            Log.d(mTag, "Load Error");
            showErrorImd(request);
        } else {
            putToMemoryCache(request, bitmap);
            putToDiskCache(request, bitmap);
            showInUI(request, bitmap);
        }

    }
}
