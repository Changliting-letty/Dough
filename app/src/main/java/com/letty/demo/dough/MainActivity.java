package com.letty.demo.dough;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.letty.demo.dough.log.LogUtil;

import java.io.File;
import java.net.URI;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image_view);
    }

    public void loadImage(View view) {
        //显示最后加载的
        Dough.getIncetance().loadImgToView(imageView, "https://pic2.zhimg.com/v2-b44546a9e25061ec41ce65ce6ca207c5_b.jpg");
        Dough.getIncetance().loadImgToView(imageView, "https://pic1.zhimg.com/80/v2-af36089754f599cf5cd2c5fe9284644c_720w.jpg");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
