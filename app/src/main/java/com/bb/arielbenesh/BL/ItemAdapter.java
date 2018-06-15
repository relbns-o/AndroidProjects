package com.bb.arielbenesh.BL;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bb.arielbenesh.R;

import java.util.List;

/**
 * item adapter for the unique recyclerview items
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    private List<Item> itemsList;
    private Context context;

    // inner class for RecyclerView.ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView myItemName;
        RelativeLayout itemContainer;

        public MyViewHolder(View view) {
            super(view);
            myItemName = (TextView)view.findViewById(R.id.lblItem);
            itemContainer = (RelativeLayout) view.findViewById(R.id.layContainer);
            context = view.getContext();
        }
    }

    public ItemAdapter(List<Item> itemsList) {
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create view from xml
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Item item = itemsList.get(position);
        // place full item name in its item place holder
        holder.myItemName.setText(item.getFullName());

        // change font to bold if identified is true
        if(item.isIdentified()){
            holder.myItemName.setTypeface(holder.myItemName.getTypeface(), Typeface.BOLD);
        }
        setColor(holder, item);

        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // invoke toast on item click for example (proof of concept)
                Toast.makeText(context, itemsList.get(position).getFullName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // choose color for item background by level value
    private void setColor(@NonNull MyViewHolder holder, Item item) {
        int level = item.getLevel();
        // get and change color from the settings that stored in SharedPreferences (user pref)
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean checkBox = prefs.getBoolean("chkForOn", true);
        if(level == -1){
            if(checkBox){
                holder.itemContainer.setBackgroundColor(Color.RED);
            }
        }
        if(isBetween(level, 0, 9)){
            holder.itemContainer.setBackgroundColor(Color.RED);
            return;
        }else if(isBetween(level, 10, 50)){
            holder.itemContainer.setBackgroundColor(Color.YELLOW);
            return;
        }else if(level>50){
            holder.itemContainer.setBackgroundColor(Color.GREEN);
            return;
        }
    }

    // check interval
    private static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    // update items count with list size
    @Override
    public int getItemCount() {
        return itemsList.size();
    }

}
