package com.vnakhoa.vku.studentnote.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.model.MenuItem;

import java.util.ArrayList;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MeniItemHolder> {

    private ArrayList<MenuItem> list;
    private Context context;

    public MenuItemAdapter(ArrayList<MenuItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MeniItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu,parent,false);
        MeniItemHolder holder = new MeniItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeniItemHolder holder, int position) {
        MenuItem menuItem = list.get(position);
        holder.txtMenu.setText(menuItem.getTitle());
        holder.txtMenu.setCompoundDrawablesWithIntrinsicBounds(menuItem.getIcon(),0,R.drawable.ic_round_chevron_right_24,0);
        holder.itemView.setOnClickListener(menuItem.getOnClickListener());
        if (position == list.size()-1) holder.itemView.setPadding(0,0,0,0);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MeniItemHolder extends RecyclerView.ViewHolder{
        TextView txtMenu;
        public MeniItemHolder(@NonNull View itemView) {
            super(itemView);
            txtMenu = itemView.findViewById(R.id.txtMenu);
        }
    }
}
