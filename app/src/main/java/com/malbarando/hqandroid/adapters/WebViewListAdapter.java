package com.malbarando.hqandroid.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.malbarando.hqandroid.R;
import com.malbarando.hqandroid.objects.WebViewItem;

import java.util.List;

/**
 * Created by Maica Albarando on 2/10/2016.
 */
public class WebViewListAdapter extends BaseAdapter {
    private final Context mContext;
    private List<WebViewItem> mData;
    private ViewHolder viewHolder;

    public WebViewListAdapter(Context mContext, List<WebViewItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.itemlist_main, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) view.findViewById(R.id.text1);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        WebViewItem item = mData.get(i);
        if (item != null) {
            viewHolder.tvName.setText(item.key);
        }

        return view;
    }

    static class ViewHolder {
        TextView tvName;
    }
}
