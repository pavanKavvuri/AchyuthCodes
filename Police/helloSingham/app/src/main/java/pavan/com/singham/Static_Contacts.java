package pavan.com.singham;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class Static_Contacts extends AppCompatActivity {


    private List<Person> persons;

    int i= R.drawable.call_1;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static__contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
        persons.add(new Person("Control Room", "0253-2305233", i));
        persons.add(new Person("Crime Branch", "253-  2392233 / 2393341", i));
        persons.add(new Person("City Traffic Branch", "0253-2305228", i));
        persons.add(new Person("Mahila Suraksha Vishesh Shakha", "0253-2305241",i));
        persons.add(new Person("Economic Offence Wing", "0253-2305230", i));
        persons.add(new Person("Nashik city police", "0253 2305233", i));


        persons.add(new Person("Nashik Road Police Station", "0253-2465533 / 2465133", i));
        persons.add(new Person("Bhadrakali Police Station", "0253-2305254 / 2305255 / 2305267", i));
        persons.add(new Person("Sarkarwada Police Station", "0253-2305214 / 2305225", i));
        persons.add(new Person("Gangapur Police Station", "0253-2305242",i));
        persons.add(new Person("Adagon Police Station", "0253-2629837 ", i));
        persons.add(new Person("Ambad Police Station", "0253-2392233 / 2393341", i));




        persons.add(new Person("Satpur Police Station", "0253-2305237 / 2305238", i));
        persons.add(new Person("Indira Nagar Police Station", "0253-2397733", i));
        persons.add(new Person("Deolali Police Station", "0253-2491233", i));
        persons.add(new Person("Bhadrakali Police Station", "0253-2305254 / 2305255 / 2305267", i));
        persons.add(new Person("Mumbai Naka", "0253 259 3300", i));
        persons.add(new Person("Mhasrul Police Station ", "0253 262 9830", i));
        persons.add(new Person("Upnagar Police Station", "0253 2305205", i));
        persons.add(new Person("CP office city  ", "0253 2305200/ 2305201", i));
        //  persons.add("CP office city  ", "0253 2305200/ 2305201", "dd");

    }

}
