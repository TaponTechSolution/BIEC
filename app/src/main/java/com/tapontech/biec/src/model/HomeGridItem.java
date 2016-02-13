package com.tapontech.biec.src.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.tapontech.biec.src.helpers.Consts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sanjay on 11-02-2016.
 */
public class HomeGridItem implements Parcelable {

    // data members
    private int gridId;
    private String gridType;
    private String gridImage;

    // constructors
    public HomeGridItem() {
        this.gridId = 0;
        this.gridType = "";
        this.gridImage = "";
    }

    // getters and setters
    public int getGridId() {
        return gridId;
    }

    public void setGridId(int gridId) {
        this.gridId = gridId;
    }

    public String getGridType() {
        return gridType;
    }

    public void setGridType(String gridType) {
        this.gridType = gridType;
    }

    public String getGridImage() {
        return gridImage;
    }

    public void setGridImage(String gridImage) {
        this.gridImage = gridImage;
    }

    // methods to get array list of grid items
    public static ArrayList<HomeGridItem> getGridItemsForArray(JSONArray jsonArray) {

        ArrayList<HomeGridItem> homeGridItemList = null;

        // parse json array and create objects
        if (jsonArray != null && jsonArray.length() > 0) {
            homeGridItemList = new ArrayList<HomeGridItem>();
            for (int i = 0; i < jsonArray.length(); i++) {
                HomeGridItem homeGridItem = null;
                try {
                    JSONObject gridItemObj = jsonArray.getJSONObject(i);
                    if (gridItemObj != null && gridItemObj.length() > 0) {
                        homeGridItem = new HomeGridItem();
                        homeGridItem.setGridId(gridItemObj.getInt(Consts.GRID_ITEM_ID));
                        homeGridItem.setGridType(gridItemObj.getString(Consts.GRID_ITEM_TYPE));
                        homeGridItem.setGridImage(gridItemObj.getString(Consts.GRID_ITEM_IMAGE));
                        homeGridItemList.add(homeGridItem);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return homeGridItemList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(gridId);
        dest.writeString(gridType);
        dest.writeString(gridImage);
    }

    public static final Parcelable.Creator<HomeGridItem> CREATOR = new Parcelable.Creator<HomeGridItem>() {
        public HomeGridItem createFromParcel(Parcel in) {
            return new HomeGridItem(in);
        }

        public HomeGridItem[] newArray(int size) {
            return new HomeGridItem[size];
        }
    };

    private HomeGridItem(Parcel in) {

        gridId = in.readInt();
        gridType = in.readString();
        gridImage = in.readString();
    }
}
