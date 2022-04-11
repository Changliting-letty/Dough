/**
 * @TIME：2022/3/30 19:03
 * @Author:clt
 * @desc: this is DownLoadImg
 * 用于retrofit下载图片
 */

package com.letty.demo.dough.loadImgs;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

public interface IDownLoadImg {
    @Streaming
    @GET("/")
    Call<ResponseBody> getImg();
}
