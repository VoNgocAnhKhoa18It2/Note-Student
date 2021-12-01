package com.vnakhoa.vku.studentnote.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.model.Lich;

import java.util.ArrayList;

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.TodayHolder>  {

    private Context context;
    private ArrayList<Lich> list;

    public TodayAdapter(Context context, ArrayList<Lich> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public TodayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_today,parent,false);
        TodayHolder holder = new TodayHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodayHolder holder, int position) {
        Lich lich = list.get(position);
        int width = holder.itemView.getLayoutParams().width;
        int height = holder.itemView.getLayoutParams().height;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,height);
        lp.topMargin = dpToPx(10,context);
        lp.rightMargin = dpToPx(10,context);
        if (position == 0) {
            lp.leftMargin = dpToPx(15,context);
        }
        if (position == (list.size()-1)) {
            lp.rightMargin = dpToPx(15,context);
        }
        holder.itemView.setLayoutParams(lp);
        holder.txtLocation.setText(lich.getLocation());
        holder.txtTitle.setText(lich.getTitle());
        holder.txtTime.setText(lich.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public class TodayHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle,txtTime,txtLocation;
        private ImageView image;
        private LinearLayout item;

        public TodayHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            image = itemView.findViewById(R.id.image);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtLocation = itemView.findViewById(R.id.txtLoacation);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
    }
}
