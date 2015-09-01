package erparchitector.flikr.com.test.network;

import erparchitector.flikr.com.test.BuildConfig;
import erparchitector.flikr.com.test.network.gson.PhotosRoot;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Xandr on 8/31/2015.
 */
public class WebClient {

    private static WebClient instance;
    private RestAdapter restAdapter;
    private WebInterface webInterface;

    private WebClient() {
    }

    public synchronized static WebClient getInstance() {
        if (instance == null)
            instance = new WebClient();
        return instance;
    }

    public PhotosRoot search(String text) {
        return getWebInterface().search(text);
    }

    private synchronized RestAdapter getRestAdapter() {
        if (restAdapter == null) {
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(WebInterface.END_POINT)
                    .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                    .build();
        }
        return restAdapter;
    }

    private synchronized WebInterface getWebInterface() {
        if (webInterface == null) {
            webInterface = getRestAdapter().create(WebInterface.class);
        }
        return webInterface;
    }

    private interface WebInterface {

        public static final String END_POINT = "https://api.flickr.com";
        //public static final String API_KEY = "YOUR_API_KEY";
        //public static final String API_KEY = "acada0ff2bb2747f40e0b227675360c9";
        public static final String API_KEY = "daffafbaa5eac3037845072619a44652";

        @GET("/services/rest/?method=flickr.photos.search&api_key=" + API_KEY +"&format=json&nojsoncallback=1&per_page=1")
        public PhotosRoot search(@Query("text") String text);
    }
}
