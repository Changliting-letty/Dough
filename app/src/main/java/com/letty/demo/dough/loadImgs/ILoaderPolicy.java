/**
 * @TIME：2022/3/27 10:00
 * @Author:clt
 * @desc: this is ILoaderPolicy  用于决定加载顺序
 */

package com.letty.demo.dough.loadImgs;

public interface ILoaderPolicy {

    //比较请求优先级
    int comparePriority(EachRequest request1, EachRequest request2);
}
