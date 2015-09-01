package erparchitector.flikr.com.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by Xandr on 8/31/2015.
 */
public class MainActivity extends Activity {

    private ImageView image;
    private EditText searchEdtx;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initController();
    }

    private void initController() {
        controller = new Controller(this, searchEdtx, image);
    }

    private void initView() {
        image = (ImageView) findViewById(R.id.image);
        searchEdtx = (EditText) findViewById(R.id.search_edtx);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (controller != null) {
            controller.onDestroy();
        }
        controller = null;
    }
}
