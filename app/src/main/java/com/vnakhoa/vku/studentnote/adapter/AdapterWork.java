package com.vnakhoa.vku.studentnote.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.activity.EditWorkDetailActivity;
import com.vnakhoa.vku.studentnote.activity.MoreWorkActivity;
import com.vnakhoa.vku.studentnote.model.CalendarDetail;

import java.util.ArrayList;

public class AdapterWork extends RecyclerView.Adapter<AdapterWork.Holder>{
    Activity activity;
    ArrayList<CalendarDetail> list;

    public AdapterWork(Activity activity, ArrayList<CalendarDetail> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_word,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        CalendarDetail cv = list.get(position);
        final String x = cv.getTitle();
        holder.txtChuDe.setText(cv.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EditWorkDetailActivity.class);
                intent.putExtra("Position",position);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView txtChuDe;
        ImageView btnShow,btnClose;
        RecyclerView rcvChiTiet;
        public Holder(@NonNull View itemView) {
            super(itemView);
            txtChuDe = itemView.findViewById(R.id.txtTitle);
        }
    }
}
