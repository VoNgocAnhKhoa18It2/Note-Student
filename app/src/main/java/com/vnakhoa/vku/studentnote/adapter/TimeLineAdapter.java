package com.vnakhoa.vku.studentnote.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.core.Config;
import com.vnakhoa.vku.studentnote.model.TimeLine;

import java.util.ArrayList;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineHolder> {
    Context context;
    ArrayList<TimeLine> list;

    public TimeLineAdapter(Context context, ArrayList<TimeLine> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TimeLineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row,parent,false);
        TimeLineHolder holder = new TimeLineHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineHolder holder, int position) {
        TimeLine timeLine = list.get(position);
        holder.txtTime.setText(timeLine.getTime());
        holder.txtTitle.setText(timeLine.getTitle());
        holder.imgDivider.setImageResource(timeLine.getImage());
        if (timeLine.getImage() == R.drawable.itemtwo){
            holder.txtTitle.setGravity(Gravity.BOTTOM);
            holder.txtTitle.setPadding(Config.dpToPx(40,context),0,0,0);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TimeLineHolder extends RecyclerView.ViewHolder{
        TextView txtTime,txtTitle;
        ImageView imgDivider;
        public TimeLineHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            imgDivider = itemView.findViewById(R.id.imgDivider);
        }
    }
}
