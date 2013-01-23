package vn.fiosoft.zop;

import vn.fiosoft.service.ZOPApplication;
import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);   
        ZOPApplication.start();
    }

   
}
