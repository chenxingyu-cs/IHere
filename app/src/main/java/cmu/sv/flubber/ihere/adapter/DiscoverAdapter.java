package cmu.sv.flubber.ihere.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import cmu.sv.flubber.ihere.entities.ITag;
import cmu.sv.flubber.ihere.ui.DiscoveryActivity;
import cmu.sv.flubber.ihere.ui.ItagDetailActivity;


/**
 * Created by zhengyiwang on 4/13/16.
 */
public class DiscoverAdapter extends RecyclerView.Adapter{

    private ArrayList<TextView> textlist;
    private ArrayList<ITag> iTagList ;
    private Context context;
    public DiscoverAdapter(ArrayList<TextView> textlist, ArrayList<ITag> iTagList, Context context){
        this.textlist = textlist;
        this.iTagList = iTagList;
        this.context = context;
    }

    public static final String ARG_ITEM_ID = "item_id";


    public void show(){

        //clear all text view
        for(TextView v : textlist){
            v.setText("");
        }




        //get a random list positions for diaplay
        int numbersNeeded = iTagList.size();

        Random rng = new Random(); // Ideally just create one instance globally
        Set<Integer> generated = new LinkedHashSet<Integer>();

        while (generated.size() < numbersNeeded && generated.size() < textlist.size())
        {
            Integer next = rng.nextInt(textlist.size());
            generated.add(next);
        }

        //display the itag
        TextView view;
        int i =0;
        for (int index : generated) {
            view = textlist.get(index);
            view.setText(iTagList.get( i).getContent());
            final int id = iTagList.get(i).getiTagId();
            i++;
            final TextView finalview = view;
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(context, ItagDetailActivity.class);
                    intent.putExtra(ARG_ITEM_ID , id);
                    finalview.setTextColor(Color.GREEN);
                    context.startActivity(intent);
                    //Toast.makeText(DiscoveryActivity.this, "Discovering...", Toast.LENGTH_SHORT).show();

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
