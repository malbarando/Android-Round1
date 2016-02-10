package com.malbarando.hqandroid.helpers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.malbarando.hqandroid.objects.WebViewItem;
import com.malbarando.hqandroid.utils.Constants;
import com.malbarando.hqandroid.utils.HQJsonRequest;
import com.malbarando.hqandroid.utils.RequestQueueSingleton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Maica Albarando on 2/10/2016.
 */
public class DataRequestHelper {

    private final Context mContext;
    private WebRequestListener mListener;

    public DataRequestHelper(Context context, WebRequestListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    public interface WebRequestListener {
        void onDataUpdate(List<WebViewItem> itemList);
    }

    public void getUrlData() {
        HQJsonRequest request = new HQJsonRequest(Constants.URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        formatResult(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueueSingleton.getInstance(mContext).addToRequestQueue(request);
    }

    private void formatResult(JSONObject response) {
        // Parse JSON, map into list
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject data = jsonParser.parse(response.toString()).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entrySet = data.entrySet();
        List<WebViewItem> list = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            WebViewItem item = gson.fromJson(data.getAsJsonObject(entry.getKey()), WebViewItem.class);
            item.key = entry.getKey();
            list.add(item);
        }
        // update listener
        if (mListener != null) {
            mListener.onDataUpdate(list);
        }
    }

    /**
     * Reformats URL by replacing parameters
     * @param url
     * @return
     */
    public static String filterUrl(String url) {
        return url.replace(Constants.PARAMS_USERID, Constants.USERID)
                .replace(Constants.PARAMS_APP_SECRETKEY, Constants.APP_SECRETKEY)
                .replace(Constants.PARAMS_CURRENCY_CODE, Constants.CURRENCY_CODE)
                .replace(Constants.PARAMS_OFFERID, Constants.OFFERID)
                .replace(Constants.PARAMS_SELECTED_VOUCHERS, Constants.SELECTED_VOUCHERS);
    }
}
