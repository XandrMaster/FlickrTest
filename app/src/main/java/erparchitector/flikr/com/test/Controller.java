package erparchitector.flikr.com.test;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import erparchitector.flikr.com.test.network.WebClient;
import erparchitector.flikr.com.test.network.gson.Photo;
import erparchitector.flikr.com.test.network.gson.PhotosRoot;
import rx.Observer;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.widget.OnTextChangeEvent;
import rx.android.widget.WidgetObservable;
import rx.functions.Func1;

/**
 * Created by Xandr on 8/31/2015.
 */
public class Controller {

    public static final int TEXT_INPUT_DELAY_MS = 1000;
    private EditText searchEdtx;
    private ImageView imageView;
    private WeakReference<Activity> activity;
    private Subscription subscription;
    private WebClient webClient;

    public Controller(Activity activity, EditText searchEdtx, ImageView imageView) {
        this.searchEdtx = searchEdtx;
        this.imageView = imageView;
        this.activity = new WeakReference<Activity>(activity);
        webClient = WebClient.getInstance();
        initRxJ();
    }

    private String getFirstFlickrImgUrl(String searchStr){
        String result = null;

        PhotosRoot photosRoot = webClient.search(searchStr);
        if (photosRoot != null && photosRoot.isValid()) {
            Photo photo = photosRoot.photos.photoList.get(0);
            if (photo != null && photo.isValid()){
                result = photo.getUrlPhoto();
            }
        }

        return result;
    }

    private void initRxJ() {
        subscription = AppObservable.bindActivity(activity.get(),
                WidgetObservable.text(searchEdtx, false))
                .debounce(TEXT_INPUT_DELAY_MS, TimeUnit.MILLISECONDS)
                .map(new Func1<OnTextChangeEvent, String>() {
                    @Override
                    public String call(OnTextChangeEvent onTextChangeEvent) {
                        //Logger.d("map1");
                        if (onTextChangeEvent != null) {
                            String searchStr = onTextChangeEvent.text().toString();
                            if (!TextUtils.isEmpty(searchStr)) {
                                return getFirstFlickrImgUrl(searchStr);
                            }
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        imageView.setImageResource(android.R.drawable.ic_input_get);
                    }

                    @Override
                    public void onNext(String imgUrl) {
                        //Logger.d("onNext");
                        if (imgUrl != null && !TextUtils.isEmpty(imgUrl)) {
                            //Picasso.with(imageView.getContext()).cancelRequest(imageView);
                            Picasso.with(imageView.getContext())
                                    .load(imgUrl)
                                    .placeholder(android.R.drawable.ic_menu_camera)
                                    .error(android.R.drawable.ic_input_get)
                                    .into(imageView);
                        }
                    }
                });
    }

    public void onDestroy() {
        //Logger.d(" onDestroy ");
        if (activity != null) {
            activity.clear();
        }
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
