package com.example.images;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class ImageDetailFragment extends Fragment {

    private String mImageUrl;
    private ImageView mImageView;
    private ProgressBar progressBar;
    private PhotoViewAttacher attacher;

    public static ImageDetailFragment newInstance(String imageUrl) {
        final ImageDetailFragment f = new ImageDetailFragment();

        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        f.setArguments(args);

        return f;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url")
                : null;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.image_fragment,
                container, false);
        mImageView = v.findViewById(R.id.image);
        attacher = new PhotoViewAttacher(mImageView);
        progressBar = v.findViewById(R.id.loading);

        attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                getActivity().finish();
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageLoader.getInstance().displayImage(mImageUrl, mImageView, new SimpleImageLoadingListener(){

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                super.onLoadingStarted(imageUri, view);
                progressBar.setProgress(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                super.onLoadingFailed(imageUri, view, failReason);
                String message = null;
                switch (failReason.getType()) {
                    case IO_ERROR:
                        message = "下载错误";
                        break;
                    case DECODING_ERROR:
                        message = "图片无法显示";
                        break;
                    case NETWORK_DENIED:
                        message = "网络有问题，无法下载";
                        break;
                    case OUT_OF_MEMORY:
                        message = "图片太大无法显示";
                        break;
                    case UNKNOWN:
                        message = "未知的错误";
                        break;
                }

                Toast.makeText(getActivity(), message,
                        Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.GONE);
                attacher.update();
            }
        });
    }


}
