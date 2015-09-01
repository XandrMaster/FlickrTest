package erparchitector.flikr.com.test.network.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Xandr on 9/1/2015.
 */
public abstract class FlickrResponse {

    private static final String STAT_OK = "ok";

    @SerializedName("stat")
    private String stat;

    protected boolean isValid(){
        return STAT_OK.compareToIgnoreCase(stat) == 0;
    }
}
