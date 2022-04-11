/**
 * @TIME：2022/3/30 19:03
 * @Author:clt
 * @desc: this is DownLoadImg
 */

package com.letty.demo.dough.loadImgs;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface IDownLoadImg {
    @Streaming
    @GET("/")
    Call<ResponseBody> getImg();
}
