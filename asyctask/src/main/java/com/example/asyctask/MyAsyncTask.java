package com.example.asyctask;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Administrator on 2017/9/13 0013.
 */

public class MyAsyncTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... params) {

        Log.d("xyz","doInBackgroud");
        publishProgress();
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("xyz","onPreExecute");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("xyz","onPostExecute");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        Log.d("xyz","onProgressUpdate");
    }
}
