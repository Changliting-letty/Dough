/**
 * @TIME：2022/3/27 9:58
 * @Author:clt
 * @desc: this is 加载顺序，先进先出
 */

package com.letty.demo.dough.loadImgs;

public class LoadPolicyFIFO implements ILoaderPolicy {

    @Override
    public int comparePriority(EachRequest request1, EachRequest request2) {
        return request2.getSerialNum() - request1.getSerialNum();
    }
}
