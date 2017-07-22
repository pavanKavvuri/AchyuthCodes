package com.mateoj.multiactivitydrawer;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class NoToolbar extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_toolbar);
    }

    @Override
    protected boolean useToolbar() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.home1:
                startActivity(new Intent(this, MainActivity.class));
                return true;

            case R.id.profile:
                startActivity(new Intent(this, OtherActivity.class));
                return true;

            case R.id.about_us :
                startActivity(new Intent(this, NoHamburger.class));
                return true;

            case R.id.log_out:
                startActivity(new Intent(this, MainActivity.class));
                return true;

            case R.id.Support:
                startActivity(new Intent(this, NoToolbar.class));
                return true;

            case R.id.lang :
                startActivity(new Intent(this, Notify.class));
                return true;


            case R.id.feedback :
                startActivity(new Intent(this, NoToolbar.class));
                return true;

            case R.id.notify :
                startActivity(new Intent(this, Notify.class));
                return true;

            case R.id.alert :
                startActivity(new Intent(this, NoToolbar.class));
                return true;


        }

        return super.onOptionsItemSelected(item);
    }
}
