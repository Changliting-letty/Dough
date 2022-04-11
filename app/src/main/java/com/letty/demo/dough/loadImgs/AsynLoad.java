/**
 * @TIME：2022/3/31 9:39
 * @Author:clt
 * @desc: this is AsynTaskLoad 加载工具类， ：选择加载器；异步加载；主线程显示
 * <p>
 * 实现子线程加载，主线程显示
 */

package com.letty.demo.dough.loadImgs;

import android.graphics.Bitmap;
import android.net.Uri;

import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.letty.demo.dough.log.LogUtil;
import com.letty.demo.dough.placeholder.PlaceHolderPolicy;

import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class AsynLoad {
    private static volatile AsynLoad instance;
    private static final String mTag = "AsynLoad";
    private EachRequest eachRequest;

    public AsynLoad(EachRequest eachRequest) {
        this.eachRequest = eachRequest;
    }

    public ConcurrentHashMap<Integer, Integer> loadSerils;

    private AsynLoad() {
        loadSerils = new ConcurrentHashMap<>();
    }

    public static AsynLoad getInstance() {
        if (instance == null) {
            synchronized (AsynLoad.class) {
                if (instance == null) {
                    instance = new AsynLoad();
                }
            }
        }
        return instance;
    }

    /**
     * 异步
     */
    public void loadAndShow(@NonNull ImageView imageView) {
        String strUri = eachRequest.getUrl();
        Uri uri = Uri.parse(strUri);
        String schema = uri.getScheme();
        AbstractLoader loader = MyLoaderManager.getInstance().getLoader(schema);
        showLoadingImgs(imageView);
        if (loader == null) {
            LogUtil.d(mTag, "Do not find the relate loader.Please Check again");
            //这里应该显示错误占位图，
            showErrorImd(imageView);
        }
        loader.getImage(eachRequest)
                .subscribe(new Observer<EachRequest>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(EachRequest request) {
                        int numberToshow = loadSerils.get(imageView.getId());
                        Bitmap bitmap = request.getBitmap();
                        if (bitmap == null) {
                            showErrorImd(imageView);
                            LogUtil.d(mTag, "Fail to load this img.");
                        } else {
                            if (request.getSerialNum() == numberToshow) {
                                //显示最后一张下载的图
                                imageView.setImageBitmap(bitmap);
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //空返回，会直接走到这里
                        showErrorImd(imageView);
                        LogUtil.d(mTag, "Fail to load this image, reason:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void setEachRequest(EachRequest eachRequest) {
        this.eachRequest = eachRequest;
    }

    public void setImgViewWidth(int imgViewWidth) {
        eachRequest.setImgViewWidth(imgViewWidth);
    }

    public void setImgViewHeight(int imgViewHeight) {
        eachRequest.setImgViewHeight(imgViewHeight);
    }

    /**
     * 显示正在加载占位图
     *
     * @param imageView
     */
    public void showLoadingImgs(@Nullable ImageView imageView) {
        //检查用户是否选择显示占位图
        PlaceHolderPolicy placeHolderPolicy = eachRequest.getPlaceHolderPolicy();
        if (placeHolderPolicy == null) return;
        //UI线程显示
        imageView.setImageResource(placeHolderPolicy.getLoadingImg());
    }

    /**
     * 显示加载失败占位符
     *
     * @param imageView
     */
    public void showErrorImd(@NonNull ImageView imageView) {
        PlaceHolderPolicy placeHolderPolicy = eachRequest.getPlaceHolderPolicy();
        //UI线程显示
        imageView.setImageResource(placeHolderPolicy.getErrorImg());
    }

    public int updateSerilNum(@IdRes Integer imageViewRid) {
        if (loadSerils.containsKey(imageViewRid)) {
            Integer v = loadSerils.get(imageViewRid) + 1;
            loadSerils.put(imageViewRid, v);
            return v;
        } else {
            loadSerils.put(imageViewRid, 1);
            return 1;
        }
    }
}
