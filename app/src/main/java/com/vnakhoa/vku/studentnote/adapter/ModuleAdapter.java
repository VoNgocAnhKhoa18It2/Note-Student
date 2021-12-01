package com.vnakhoa.vku.studentnote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vnakhoa.vku.studentnote.core.Config;
import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.model.Module;

import java.util.ArrayList;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleHolder> {

    private Context context;
    private ArrayList<Module> list;

    public ModuleAdapter(Context context, ArrayList<Module> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ModuleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_module,parent,false);
        ModuleHolder moduleHolder = new ModuleHolder(view);
        return moduleHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleHolder holder, int position) {
        Module module = list.get(position);
        int width = holder.itemView.getLayoutParams().width;
        int height = holder.itemView.getLayoutParams().height;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,height);
        lp.topMargin = Config.dpToPx(12,context);
        if (position % 2 == 0) {
            lp.leftMargin = Config.dpToPx(15,context);
        } else {
            lp.leftMargin = Config.dpToPx(7,context);
        }
        if (position == (list.size()-1) || position == (list.size()-2)) {
            lp.bottomMargin = Config.dpToPx(12,context);
        }
        holder.itemView.setLayoutParams(lp);
        holder.image.setImageResource(R.drawable.ic_round_school_24);
        holder.txtTitle.setText(module.getNameModule());
        holder.txtTeacher.setText(module.getTeacher());
        holder.txtTime.setText(module.getTime());
        holder.txtLocation.setText(module.getLocaltion());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ModuleHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle,txtTeacher,txtLocation,txtTime;
        private ImageView image;
        private LinearLayout item;

        public ModuleHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            image = itemView.findViewById(R.id.image);
            txtTeacher = itemView.findViewById(R.id.txtTeacher);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtLocation = itemView.findViewById(R.id.txtLoacation);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
    }
}
