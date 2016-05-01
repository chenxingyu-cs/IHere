package cmu.sv.flubber.ihere.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import cmu.sv.flubber.ihere.entities.ITag;



/**
 * Created by zhengyiwang on 4/13/16.
 */
public class DiscoverAdapter extends RecyclerView.Adapter{

    private ArrayList<TextView> textlist;
    private ArrayList<ITag> iTagList ;

    public DiscoverAdapter(ArrayList<TextView> textlist, ArrayList<ITag> iTagList){
        this.textlist = textlist;
        this.iTagList = iTagList;
    }


    public void show(){

        //clear all text view
        for(TextView v : textlist){
            v.setText("");
        }




        //get a random list positions for diaplay
        int numbersNeeded = iTagList.size();

        Random rng = new Random(); // Ideally just create one instance globally
        Set<Integer> generated = new LinkedHashSet<Integer>();

        while (generated.size() < numbersNeeded)
        {
            Integer next = rng.nextInt(textlist.size());
            generated.add(next);
        }

        //display the itag
        TextView view;
        int i =0;
        for (int index : generated) {
            view = textlist.get(index);
            view.setText(iTagList.get( i++).getContent());
            final TextView finalview = view;
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    finalview.setTextColor(99999);
                }
            });
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
