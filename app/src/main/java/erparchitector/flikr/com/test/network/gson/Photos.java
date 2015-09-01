package erparchitector.flikr.com.test.network.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Xandr on 9/1/2015.
 */
public class Photos{
    @SerializedName("photo")
    public List<Photo> photoList;
}
