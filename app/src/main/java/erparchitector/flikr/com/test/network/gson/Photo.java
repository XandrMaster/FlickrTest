package erparchitector.flikr.com.test.network.gson;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Xandr on 9/1/2015.
 */
public class Photo {

    private static final String PHOTO_URL_TEMPLATE
            = "https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg";

    @SerializedName("id")
    public String id;

    @SerializedName("farm")
    public String farm;

    @SerializedName("server")
    public String server;

    @SerializedName("secret")
    public String secret;

    public boolean isValid(){
        return !TextUtils.isEmpty(id)
                && !TextUtils.isEmpty(farm)
                && !TextUtils.isEmpty(server)
                && !TextUtils.isEmpty(secret);
    }

    public String getUrlPhoto(){
        String url = PHOTO_URL_TEMPLATE
                .replace("{farm-id}", farm)
                .replace("{server-id}", server)
                .replace("{id}", id)
                .replace("{secret}", secret);

        //Logger.d(" url: " + url);
        return url;
    }
}
