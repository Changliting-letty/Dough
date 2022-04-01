package com.letty.demo.dough;

/*
 *常量类，内部定义静态枚举类
 * 既可以对不同常量进行分类，并且使用静态枚举类实现定义静态常量码以及其描述信息
 * 一些可供选择的策略
 * 比如内存缓存策略
 * 硬盘缓存策略
 * 图片加载策略
 * */
public class Policy {
    /**
     * 内存缓存策略
     */
    public static enum MemoryCachePolicy {
        None(0, "No cache"),
        LRU(1, "cache the final result to memoryCache");


        private final Integer value;
        private final String des;

        private MemoryCachePolicy(Integer value, String des) {
            this.value = value;
            this.des = des;
        }

        public Integer getValue() {
            return value;
        }

        public String getDes() {
            return des;
        }
    }

    /**
     * 磁盘缓存策略
     */
    public static enum DiskCachePolicy {
        None(0, "No cache"), //表示不缓存
        Result(1, "cache the final result"); //表示缓存结果图

        private final Integer value;
        private final String des;

        private DiskCachePolicy(Integer value, String des) {
            this.value = value;
            this.des = des;
        }

        public Integer getValue() {
            return value;
        }

        public String getDes() {
            return des;
        }
    }

    /**
     * 图片加载策略  FIFO or LIFO
     */
    public static enum LoadPolicy {
        FIFO(0, "FIFO"),
        LIFO(1, "LIFO");
        private final Integer value;
        private final String des;

        private LoadPolicy(Integer value, String des) {
            this.value = value;
            this.des = des;
        }

        public Integer getValue() {
            return value;
        }

        public String getDes() {
            return des;
        }
    }

    /**
     * 占位图设置
     */
    public static enum PlaceHolderPolicy {
        None(0, null, "No placeHolder"),
        LOADINGIMG(1, R.drawable.load, "Loding img"),
        ErrorIMG(1, R.drawable.error, "Error  img");
        private final Integer value;
        private final Integer res;
        private final String des;

        private PlaceHolderPolicy(Integer value, Integer res, String des) {
            this.value = value;
            this.res = res;
            this.des = des;
        }

        public Integer getValue() {
            return value;
        }

        public Integer getRes() {
            return res;
        }

        public String getDes() {
            return des;
        }
    }

}
