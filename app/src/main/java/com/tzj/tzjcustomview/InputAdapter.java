package com.tzj.tzjcustomview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/9.
 */

public class InputAdapter extends BaseAdapter {

    private List<String> list;
    private OnCommentListener onCommentClick;
    private Context context;

    public void setOnCommentClick(OnCommentListener onCommentClick) {
        this.onCommentClick = onCommentClick;
    }

    public InputAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_input_list, viewGroup, false);
            holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_content.setText(list.get(i));
        holder.tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCommentClick != null) {
                    onCommentClick.onComment(holder.tv_comment, i);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_comment;
        TextView tv_content;

    }

    interface OnCommentListener {
        void onComment(View view, int position);
    }
}
