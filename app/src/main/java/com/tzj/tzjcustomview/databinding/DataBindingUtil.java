package com.tzj.tzjcustomview.databinding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by tangzhijie on 2018/3/21.
 */

public class DataBindingUtil {

    //参数要一一对应
    //获取xml中自定义的绑定属性
    @BindingAdapter({"imageUrl", "error"})
    public static void loadImage(ImageView imageView, String url, Drawable error) {
        Log.i("MyLog", url);
        Glide.with(imageView.getContext())
                .load(url)
                .error(error)
                .into(imageView);
    }

    @BindingAdapter({"imageUrl","msg"})
    public static void loadImage2(ImageView imageView, String url,String msg) {
        Log.i("MyLog", url);
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }
}
