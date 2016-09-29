package com.tzj.tzjcustomview.puzzle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tzj.tzjcustomview.R;
import com.tzj.tzjcustomview.ScreenUtil;

import java.util.List;

/**
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 */
public class PuzzleAdapter extends BaseAdapter {

    private Context context;
    private List<ItemBean> list;
    private int mode;

    public PuzzleAdapter(Context context, List<ItemBean> list, int mode) {
        this.context = context;
        this.list = list;
        this.mode = mode;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.puzzle_item, parent, false);
            holder.iv = (ImageView) convertView.findViewById(R.id.iv);

            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.iv.getLayoutParams();
            lp.width = ScreenUtil.getScreenWidth(context) / mode;
            lp.height = ScreenUtil.getScreenWidth(context) / mode;
            holder.iv.setLayoutParams(lp);
            holder.iv.setScaleType(ImageView.ScaleType.FIT_XY);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.iv.setImageBitmap(list.get(position).getBitmap());
        return convertView;
    }

    class Holder {
        ImageView iv;
    }
}
