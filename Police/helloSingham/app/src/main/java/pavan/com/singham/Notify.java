package pavan.com.singham;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Notify extends AppCompatActivity {

    TextView MSG, MSG1, MSG2, MSG3;

    ArrayList<String> Notify_list;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        Notify_list = new ArrayList<String>();

        ArrayHelper naveen = new ArrayHelper(this);







        MSG= (TextView) findViewById(R.id.n0);
        MSG1= (TextView) findViewById(R.id.n1);
        MSG2= (TextView) findViewById(R.id.n2);
        MSG3= (TextView) findViewById(R.id.n3);



      /*  SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
       // int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for(int i=0;i<size;i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
*/


        //Intent intent = getIntent();

       // String message = intent.getStringExtra(FirebaseMessagingService.EXTRA_MESSAGE);

        MSG.setText(naveen.getArray("Notif_Array").get(0));

 //       String s= naveen.getArray("Notif_Array").get(1);

   //     String s1=naveen.getArray("Notif_Array").get(2);

    //   MSG1.setText(String.valueOf(naveen.getArray("Notif_Array").get(1)));

      // MSG2.setText(String.valueOf(naveen.getArray("Notif_Array").get(2)));

        //MSG3.setText(naveen.getArray("Notif_Array").get(3));






     //   Notify_list.add(message);

        //this adds an element to the list.


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Soon this action will be place", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
