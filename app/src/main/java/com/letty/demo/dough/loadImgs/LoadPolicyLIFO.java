/**
 * @TIME：2022/3/27 10:04
 * @Author:clt
 * @desc: this is LoadPolicyLIFO  加载顺序，后进先出
 */

package com.letty.demo.dough.loadImgs;

public class LoadPolicyLIFO implements ILoaderPolicy {
    @Override
    public int comparePriority(EachRequest request1, EachRequest request2) {
        return request1.getSerialNum() - request2.getSerialNum();
    }
}
