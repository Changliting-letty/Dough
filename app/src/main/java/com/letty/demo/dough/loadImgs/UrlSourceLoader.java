package com.letty.demo.dough.loadImgs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.letty.demo.dough.Dough;
import com.letty.demo.dough.bitmaputil.BitmapTransform;
import com.letty.demo.dough.log.LogUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
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

    //采样下载，避免大图OOM
    @Override
    public Bitmap onLoad(EachRequest request) {
        Bitmap bitmap = null;
        try {
            URL url;
            HttpURLConnection connection = null;
            url = new URL(request.getUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream = connection.getInputStream();
            //转化成BufferedInputStream
            final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            //标记一下，reset后会重置到这个位置
            bufferedInputStream.mark(inputStream.available());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;//只对宽高，不全部读图到内存
            options.inPreferredConfig = Dough.getIncetance().getConfig().getBitmapConfig();
            //第一次读
            bitmap = BitmapFactory.decodeStream(bufferedInputStream, null, options);
            //重置
            bufferedInputStream.reset();
            int imageviewWidth=request.getImgViewWidth();
            int imageviewHeight =request.getImgViewHeight();
            BitmapTransform.getSampleSizeWithOptions(options, imageviewWidth, imageviewHeight);
            options.inJustDecodeBounds = false;//复位
            //第二次读
            bitmap = BitmapFactory.decodeStream(bufferedInputStream, null, options);
            bufferedInputStream.close();
            inputStream.close();
            putToDiskCache(request, bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            LogUtil.d(mTag, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.d(mTag, e.getMessage());
        } finally {
            return bitmap;
        }
    }

   /* //试试retrofit,出错，Failed to create image decoder with message 'unimplemented'
    @Override
    public Bitmap onLoad(EachRequest request) {
        Bitmap bitmap = null;
        try {
            String imgUrl=request.getUrl();
            //不设baseurl会出错
            Retrofit retrofit = new Retrofit.Builder().baseUrl(imgUrl).build();
            IDownLoadImg request_interface = retrofit.create(IDownLoadImg.class);
            Call<ResponseBody> call = request_interface.getImg();
            ResponseBody responseBody= call.execute().body();
            InputStream inputStream = responseBody.byteStream();
            //转化成BufferedInputStream
            final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            //标记一下，reset后会重置到这个位置
            bufferedInputStream.mark(inputStream.available());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;//只对宽高，不全部读图到内存
            options.inPreferredConfig = Dough.getIncetance().getConfig().getBitmapConfig();
            //第一次读
            bitmap = BitmapFactory.decodeStream(bufferedInputStream, null, options);
            //重置
            bufferedInputStream.reset();
            int imageviewWidth=request.getImgViewWidth();
            int imageviewHeight =request.getImgViewHeight();
            BitmapTransform.getSampleSizeWithOptions(options, imageviewWidth, imageviewHeight);
            options.inJustDecodeBounds = false;//复位
            //第二次读
            bitmap = BitmapFactory.decodeStream(bufferedInputStream, null, options);
            bufferedInputStream.close();
            inputStream.close();
            putToDiskCache(request, bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            LogUtil.d(mTag, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.d(mTag, e.getMessage());
        } finally {
            return bitmap;
        }
    }*/

}
