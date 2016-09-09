package com.tzj.tzjcustomview.rvgalleryview;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tzj.tzjcustomview.R;
import com.tzj.tzjcustomview.TabVpDetailActivity;

import java.util.List;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.Holder> {

    private List<Integer> list;
    private Context context;

    public RvAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_card_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.imageView.setImageResource(list.get(position));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                context.startActivity();
                Intent intent = new Intent(context, TabVpDetailActivity.class);
                intent.putExtra("id",list.get(position));
                startActivity(view, intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv);
        }
    }

    //跳转时，添加图片过度动画效果
    private void startActivity(View view, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //这里的字符串需要和目标页面上的控件的android:transitionName="transition_animation_news_photos"对应
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation((Activity) context, view, "transition_animation_news_photos");
            context.startActivity(intent, options.toBundle());
        } else {
            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation((Activity) context, view, "transition_animation_news_photos");
            ActivityCompat.startActivity((Activity) context, intent, options.toBundle());
        }
    }
}
