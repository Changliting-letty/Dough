package com.letty.demo.dough;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
        ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=(ImageView)findViewById(R.id.image_view);
    }
    public  void loadImage(View view){
        DoughConfig config=new DoughConfig.Builder(MyApplication.getContext())
                .setMemoryCache(Policy.MemoryCachePolicy.LRU.getValue())
                .setDiskCache(Policy.DiskCachePolicy.Result.getValue())
                .setLoaderPolicy(Policy.LoadPolicy.FIFO.getValue())
                .setPlaceholder().build();
        Dough.init(config).loadImgToView(imageView,"https://www.tencent.com/img/index/tencent_logo.png");
    }}