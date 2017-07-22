package com.mateoj.multiactivitydrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class Static_Contacts extends BaseActivity {


    private List<contacts> persons;

    int i= R.drawable.call_1;

    ScrollView scrollView;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static__contacts);




        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        assert mRecyclerView != null;
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        initializeData();
        mAdapter = new MyAdapter(persons);
        mRecyclerView.setAdapter(mAdapter);

    }


    private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new contacts("Control Room", "0253-2305233", i));
        persons.add(new contacts("Crime Branch", "253-  2392233 / 2393341", i));
        persons.add(new contacts("City Traffic Branch", "0253-2305228", i));
        persons.add(new contacts("Mahila Suraksha Vishesh Shakha", "0253-2305241",i));
        persons.add(new contacts("Economic Offence Wing", "0253-2305230", i));
        persons.add(new contacts("Nashik city police", "0253 2305233", i));


        persons.add(new contacts("Nashik Road Police Station", "0253-2465533 / 2465133", i));
        persons.add(new contacts("Bhadrakali Police Station", "0253-2305254 / 2305255 / 2305267", i));
        persons.add(new contacts("Sarkarwada Police Station", "0253-2305214 / 2305225", i));
        persons.add(new contacts("Gangapur Police Station", "0253-2305242",i));
        persons.add(new contacts("Adagon Police Station", "0253-2629837 ", i));
        persons.add(new contacts("Ambad Police Station", "0253-2392233 / 2393341", i));





    }

/*
 @Override
    protected boolean useToolbar() {
        return false;
    }

    @Override
    protected boolean useDrawerToggle() {
        return false;
    }
*/


    @Override
    protected boolean useDrawerToggle() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // if (item.getItemId() == R.id.action_noHamburger)
        //   return true;

        if (item.getItemId() == android.R.id.home)
            // onBackPressed();
            startActivity(new Intent(this, MainActivity.class));


        return super.onOptionsItemSelected(item);
    }
}