package com.tapontech.biec.src.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.tapontech.biec.R;
import com.tapontech.biec.src.helpers.Consts;
import com.tapontech.biec.src.model.HomeGridItem;

/**
 * Created by Sanjay on 12-02-2016.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // The activity is being created.
        setContentView(R.layout.base_layout);

        Bundle bundle = getIntent().getExtras();
        HomeGridItem homeGridItem = bundle.getParcelable("homeGridItem");

        setUpFragment(homeGridItem);

        // Set up the action bar to show a back button.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // set the title for action bar button.
        getSupportActionBar().setTitle(homeGridItem.getGridType());
        // remove the application icon in action bar.
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    // navigates to the fragment based in item selected
    private void setUpFragment(HomeGridItem homeGridItem) {

        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        // load the fragment based on the grid item type
        if (homeGridItem.getGridType().trim().equalsIgnoreCase(Consts.GRID_ITEM_EVENTS)) {
            fragment = new FragmentBase().newInstance();
        } else if (homeGridItem.getGridType().trim().equalsIgnoreCase(Consts.GRID_ITEM_SITEPLAN)) {

        } else if (homeGridItem.getGridType().trim().equalsIgnoreCase(Consts.GRID_ITEM_ROUTEMAP)) {

        } else if (homeGridItem.getGridType().trim().equalsIgnoreCase(Consts.GRID_ITEM_NEWS)) {

        } else if (homeGridItem.getGridType().trim().equalsIgnoreCase(Consts.GRID_ITEM_GALLERY)) {

        } else if (homeGridItem.getGridType().trim().equalsIgnoreCase(Consts.GRID_ITEM_CONTACT)) {

        }

        // load fragment to the view
        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }

}
