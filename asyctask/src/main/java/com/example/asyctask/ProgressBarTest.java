package com.example.asyctask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;

/**
 * Created by Administrator on 2017/9/14 0014.
 */

public class ProgressBarTest extends Activity {

    private ProgressBar mProgressBar;
    private MyAsyncTask myAsyncTask;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressbar);
        mProgressBar = findViewById(R.id.progressBar2);
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(myAsyncTask != null && myAsyncTask.getStatus() == AsyncTask.Status.RUNNING)
            myAsyncTask.cancel(true);
    }

    private class MyAsyncTask extends AsyncTask<Void, Integer, Void>{
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(isCancelled())
                return;
            mProgressBar.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < 100; i++){
                if(isCancelled())
                    break;
                publishProgress(i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
