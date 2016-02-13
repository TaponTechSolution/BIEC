package com.tapontech.biec.src.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Sanjay on 11-02-2016.
 */
public class AssetFileReader {

    public AssetFileReader() {

    }

    /**
     *
     * @param context Context
     * @param fileName String specifying the file name that must be read
     * @return String Contents of the specified file as a string
     */
    public static String  read(Context context, String fileName) {

        String data = null;

        try {
            InputStream is;
            byte[] buffer = null;
            AssetManager assetMgr = context.getResources().getAssets();
            is = assetMgr.open(fileName);
            int size = is.available(); //size of the file in bytes
            buffer = new byte[size]; //declare the size of the byte array with size of the file
            is.read(buffer); //read file
            is.close(); //close file

            // Store text file data in the string variable
            data = new String(buffer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            data = null;
        }

        return data;
    }
}
