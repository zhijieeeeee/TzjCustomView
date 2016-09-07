package com.tzj.tzjcustomview.rvgalleryview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tzj.tzjcustomview.R;
import com.tzj.tzjcustomview.rvgalleryview.library.CardAdapterHelper;

import java.util.List;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class RvAdapterWithScale extends RecyclerView.Adapter<RvAdapterWithScale.Holder> {

    private List<Integer> list;
    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();

    public RvAdapterWithScale(List<Integer> list) {
        this.list = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_item, parent, false);
        mCardAdapterHelper.onCreateViewHolder(parent, v);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        holder.imageView.setImageResource(list.get(position));
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
