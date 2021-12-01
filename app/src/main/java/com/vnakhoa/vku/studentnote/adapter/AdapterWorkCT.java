package com.vnakhoa.vku.studentnote.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.activity.EditWorkDetailActivity;
import com.vnakhoa.vku.studentnote.model.CalendarDetail;
import com.vnakhoa.vku.studentnote.model.Work;

import java.util.ArrayList;

public class AdapterWorkCT extends RecyclerView.Adapter<AdapterWorkCT.Holder>{
    Activity activity;
    ArrayList<Work> list;

    public AdapterWorkCT(Activity activity, ArrayList<Work> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.itemword_ct,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        final Work work = list.get(position);
        String date = (!work.getDayOfWeek().equals("CN")) ? "Thứ "+((String) work.getDayOfWeek()).substring(1) : "Chủ Nhật";
        holder.txtTime.setText(work.getTime());
        holder.txtThu.setText(date);
        holder.txtAddress.setText(work.getLocation());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditWorkDetailActivity.txtDiaChi.getEditText().setText(work.getLocation());
                int positionThu = (!work.getDayOfWeek().equals("CN")) ? Integer.parseInt(work.getDayOfWeek().substring(1))-2 : 6;
                EditWorkDetailActivity.thu.setSelection(positionThu);
                String bd = work.getTime().substring(0,5);
                String kt = work.getTime().substring(7);
                EditWorkDetailActivity.txtGioKT.setText(kt);
                EditWorkDetailActivity.txtGioBD.setText(bd);
                EditWorkDetailActivity.positionWork = position;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView txtThu,txtTime,txtAddress;
        public Holder(@NonNull View itemView) {
            super(itemView);
            txtThu = itemView.findViewById(R.id.txtThu);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
    }
}
