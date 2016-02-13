package com.tapontech.biec.src.model;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

/**
 * Created by Sanjay on 11-02-2016.
 *
 */
@ReportsCrashes(
        formKey = "",
        mailTo = "info.tapontech@gmail.com, sanjay.kns2@gmail.com, umeshbr.patel@gmail.com")
public class BIECApp extends Application{

    private static BIECApp singleton;

    public static BIECApp getInstance(){
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        singleton = this;
        ACRA.init(this);

        initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        //config.memoryCacheExtraOptions(100000, 100000);
        //config.diskCacheExtraOptions(100000, 100000, null);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(100 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
