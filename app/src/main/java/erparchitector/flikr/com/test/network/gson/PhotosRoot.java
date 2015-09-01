package erparchitector.flikr.com.test.network.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Xandr on 8/31/2015.
 */

public class PhotosRoot extends FlickrResponse{

    @SerializedName("photos")
    public Photos photos;

    @Override
    public boolean isValid() {
        return super.isValid()
                && photos != null
                && photos.photoList != null
                && !photos.photoList.isEmpty();
    }
}
