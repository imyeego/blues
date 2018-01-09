package com.example.okhttp3;
import android.util.Log;

/**
 * Created by Administrator on 2017/9/30 0030.
 */

public class L {
    private static boolean debug = true;
    private static final String TAG = "Imooc Okhttp!";

    public static void e(String msg){
        if(debug){
            Log.d(TAG,msg);
        }
    }
}
