package pavan.com.singham;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pavan on 22/8/16.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    public static Context c;



    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView POL_STA,Con_NUM;
        public ImageView DIA;

        CardView cv;
        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            POL_STA = (TextView)itemView.findViewById(R.id.station);
            Con_NUM = (TextView)itemView.findViewById(R.id.callNum);
            DIA = (ImageView)itemView.findViewById(R.id.DIA);
        }
    }

    List<Person> persons;

   MyAdapter(List<Person> persons){
        this.persons = persons;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_dataset, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder personViewHolder, int i) {
        personViewHolder.POL_STA.setText(persons.get(i).name);
        personViewHolder.Con_NUM.setText(persons.get(i).num);
        personViewHolder.DIA.setImageResource(persons.get(i).photoId);
    }


    // Return the size of your dataset (invoked by the layout manager)

    @Override
    public int getItemCount() {
        return persons.size();
            }


}
