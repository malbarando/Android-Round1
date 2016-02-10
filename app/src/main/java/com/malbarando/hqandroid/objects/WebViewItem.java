package com.malbarando.hqandroid.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maica Albarando on 2/10/2016.
 */
public class WebViewItem implements Parcelable {
    public String key;
    public String url;
    public String filePath;
    public String namespace;
    public boolean cache;
    public List<String> params;

    public static final String TAG = "WEBVIEW_DATA";

    protected WebViewItem(Parcel in) {
        key = in.readString();
        url = in.readString();
        filePath = in.readString();
        namespace = in.readString();
        cache = in.readByte() != 0;
        if (in.readByte() == 0) {
            params = new ArrayList<>();
            in.readList(params, String.class.getClassLoader());
        } else {
            params = null;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(url);
        dest.writeString(filePath);
        dest.writeString(namespace);
        dest.writeByte((byte) (cache ? 1 : 0));
        if (params == null) {
            dest.writeByte((byte) (0));
        } else {
            dest.writeByte((byte) (1));
            dest.writeList(params);
        }

        dest.writeString(namespace);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<WebViewItem> CREATOR = new Parcelable.Creator<WebViewItem>() {
        @Override
        public WebViewItem createFromParcel(Parcel in) {
            return new WebViewItem(in);
        }

        @Override
        public WebViewItem[] newArray(int size) {
            return new WebViewItem[size];
        }
    };

}