package com.letty.demo.dough.loadImgs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.letty.demo.dough.Dough;
import com.letty.demo.dough.bitmaputil.BitmapTransform;
import com.letty.demo.dough.log.LogUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/*
  网络图片加载器
 *
 * */
public class UrlSourceLoader extends AbstractLoader {
    private final String mTag = "UrlSourceLoader"; //用于日志tag

    @Override
    public void onLoad(EachRequest request) {
        String imgUrl = request.getUrl();
        if (imgUrl == null || imgUrl.equals(""))
            return;
        //有handler 注意内存泄漏的问题
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(imgUrl + "/")
                .build();
        IDownLoadImg request_interface = retrofit.create(IDownLoadImg.class);
        Call<ResponseBody> call = request_interface.getImg();
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call call, Response response) {
                //拿到图片后进行解析
                try {
                    //控件宽高
                    ImageView imageView = request.getImageView();
                    int imageviewWidth = ImageViewUtil.getImageViewWidth(imageView);
                    int imageviewHeight = ImageViewUtil.getImageViewHeight(imageView);
                    ResponseBody responseBody = (ResponseBody) response.body();
                    InputStream inputStream = responseBody.byteStream();
                    //转化成BufferedInputStream
                    final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                    //标记一下，reset后会重置到这个位置
                    bufferedInputStream.mark(inputStream.available());
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;//只对宽高，不全部读图到内存
                    options.inPreferredConfig = Dough.getIncetance().getConfig().getBitmapConfig();
                    Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream, null, options);
                    //表示时第一次执行，此时只是为了获取Bounds
                    //第一次读取图片宽高信息，读完之后，要为第二次读取做准备，将流重置
                    bufferedInputStream.reset();
                    //获取采样比
                    BitmapTransform.getSampleSizeWithOptions(options, imageviewWidth, imageviewHeight);
                    options.inJustDecodeBounds = false;//复位
                    //第二次读
                    bitmap = BitmapFactory.decodeStream(bufferedInputStream, null, options);
                    bufferedInputStream.close();
                    if (bitmap == null) {
                        Log.d(mTag, "Load Error");
                        showErrorImd(request);
                    } else {
                        putToMemoryCache(request, bitmap);
                        putToDiskCache(request, bitmap);
                        showInUI(request, bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                LogUtil.d(mTag, "Network Loading failure.");
                showErrorImd(request);
            }
        });
    }

}
