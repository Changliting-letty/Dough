/**
 * @TIME：2022/3/31 9:39
 * @Author:clt
 * @desc: this is AsynTaskLoad
 * <p>
 * 异步加载
 */

package com.letty.demo.dough.loadImgs;

import android.os.AsyncTask;

import com.letty.demo.dough.log.LogUtil;

public class AsynTaskLoad extends AsyncTask<EachRequest, Integer, Object> {
    private static final String mTag = "AsynTaskLoad";

    public AsynTaskLoad() {
        super();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Object o) {
        super.onCancelled(o);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    /**
     * 异步加载
     * @param eachRequests
     * @return
     */
    @Override
    protected Object doInBackground(EachRequest... eachRequests) {
        String schema = LoadPathUtil.parseSchema(eachRequests[0].getUrl()) + "://";
        AbstractLoader loader = MyLoaderManager.getInstance().getLoader(schema);
        if (loader == null) {
            LogUtil.d(mTag, "do not find the relate loader.Please Check again");
            return null;
        }
        loader.load(eachRequests[0]);
        return null;
    }
}
