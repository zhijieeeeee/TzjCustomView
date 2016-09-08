package com.tzj.tzjcustomview.rvgalleryview;

import android.content.Context;
import android.content.Intent;
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

    public RvAdapter(Context context,List<Integer> list) {
        this.context=context;
        this.list = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_card_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.imageView.setImageResource(list.get(position));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, TabVpDetailActivity.class));
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
}
