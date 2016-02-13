package com.tapontech.biec.src.helpers;

import android.content.Context;

import com.tapontech.biec.src.model.BIECApp;
import com.tapontech.biec.src.utils.AssetFileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Sanjay on 11-02-2016.
 */
public class AppConfigMgr {

    public static final String  appConfigMainKey = "AppConfig";

    private static AppConfigMgr appConfigMgr = null;
    private boolean isInitiaized = false;

    private HashMap<String, JSONArray> gridItemsDataMap = null;

    //grid items data
    public static final String gridItemTypeKey = "homeGridItems";

    public static AppConfigMgr getInstance()
    {
        if(appConfigMgr == null)
        {
            appConfigMgr = new AppConfigMgr();
        }

        if(BIECApp.getInstance() != null)
            appConfigMgr.init(BIECApp.getInstance().getApplicationContext(), "appConfig.json");

        return appConfigMgr;
    }

    // Initialize the class
    public void init(Context context, String fileName)
    {
        if(!this.isInitiaized)
        {
            String jsonData = AssetFileReader.read(context, fileName);
            getAllDataFromJSONString(jsonData);
            this.isInitiaized = true;
        }
    }

    private void getAllDataFromJSONString(String jsonData) {

        JSONObject appConfigJSON;
        try {
            appConfigJSON = new JSONObject(jsonData);

            JSONObject appConfigObj = appConfigJSON.getJSONObject(appConfigMainKey);

            // grid item data
            JSONArray gridItemDataArrayObj = appConfigObj.getJSONArray(gridItemTypeKey);
            gridItemsDataMap = getGridItemsData(gridItemTypeKey, gridItemDataArrayObj);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // get grid items data from the json
    private HashMap<String, JSONArray> getGridItemsData(String str, JSONArray jsonArray){

        HashMap<String, JSONArray> dataMap = new HashMap<String, JSONArray>();

        if(jsonArray.length() > 0){
            dataMap.put(str, jsonArray);
        }

        return dataMap;
    }

    // get grid items data
    public JSONArray getGridItemsDataForKey(String key){ return gridItemsDataMap.get(key);}
}
