package com.tapontech.biec.src.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.tapontech.biec.R;
import com.tapontech.biec.src.helpers.AppConfigMgr;
import com.tapontech.biec.src.model.HomeGridItem;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Sanjay on 03-02-2016.
 */
public class HomeGridView extends Activity {

    private GridView homeGridItemsView;
    private ImageView homeBannerImg;

    private GridAdapter mGridAdapter;

    private Context mContext = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // The activity is being created.
        setContentView(R.layout.home_screen_view);

        AppConfigMgr appConfigMgr = AppConfigMgr.getInstance();

        JSONArray gridItemsArray = appConfigMgr.getGridItemsDataForKey(AppConfigMgr.gridItemTypeKey);

        // inflate the layout elements
        homeBannerImg = (ImageView) findViewById(R.id.homeBannerImg);
        homeGridItemsView = (GridView) findViewById(R.id.homeGridView);

        getHomeGridView(gridItemsArray);

    }

    // create home grid item view
    private void getHomeGridView(JSONArray jsonArray) {
        if (jsonArray != null && jsonArray.length() > 0) {
            final ArrayList<HomeGridItem> homeGridItemsList = HomeGridItem.getGridItemsForArray(jsonArray);
            if (homeGridItemsList != null && homeGridItemsList.size() > 0) {
                this.mGridAdapter = new GridAdapter(mContext, R.layout.home_grid_item_layout, homeGridItemsList);
                if (this.homeGridItemsView != null && this.mGridAdapter != null) {
                    homeGridItemsView.setAdapter(this.mGridAdapter);
                    mGridAdapter.notifyDataSetChanged();
                }

                if (this.homeGridItemsView.getVisibility() == View.VISIBLE) {
                    this.homeGridItemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                            onListViewItemSelected(position, homeGridItemsList);
                        }
                    });
                }
            }
        }
    }

    // on item click listener for the grid items
    protected void onListViewItemSelected(int position, ArrayList<HomeGridItem> homeGridItemsList) {

        if (homeGridItemsList != null && homeGridItemsList.size() > 0) {
            HomeGridItem homeGridItem = homeGridItemsList.get(position);
            if (homeGridItem != null) {
                Intent intent = new Intent(this, BaseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("homeGridItem", homeGridItem);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }

    // adapter to show the list of grid items
    public class GridAdapter extends ArrayAdapter {

        Context context;
        int resource;
        ArrayList<HomeGridItem> gridItems;

        public GridAdapter(Context context, int resource, ArrayList<HomeGridItem> gridItems) {
            super(context, resource, gridItems);
            this.context = context;
            this.resource = resource;
            this.gridItems = gridItems;
        }

        public class ViewHolder {
            public ImageView imageView;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            ViewHolder viewHolder;

            if (view == null) {
                LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                // get the view for assign data to list view
                view = vi.inflate(R.layout.home_grid_item_layout, null);

                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) view.findViewById(R.id.gridItem);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            HomeGridItem homeGridItem = gridItems.get(position);
            if (homeGridItem != null) {
                String moduleDrawable = homeGridItem.getGridImage();

                if (moduleDrawable != null) {
                    int iconId = getResources().getIdentifier(moduleDrawable, "drawable", mContext.getPackageName());

                    Bitmap moduleIcon = BitmapFactory.decodeResource(getResources(), iconId);
                    viewHolder.imageView.setImageBitmap(moduleIcon);
                }
            }
            return view;
        }
    }
}
