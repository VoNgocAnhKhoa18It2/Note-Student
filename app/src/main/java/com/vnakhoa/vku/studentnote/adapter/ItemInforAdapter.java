package com.vnakhoa.vku.studentnote.adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vnakhoa.vku.studentnote.R;
import com.vnakhoa.vku.studentnote.activity.EditInforActivity;
import com.vnakhoa.vku.studentnote.core.Config;
import com.vnakhoa.vku.studentnote.model.ItemInfor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ItemInforAdapter extends RecyclerView.Adapter<ItemInforAdapter.ItemInforHolder> {

    private Context context;
    private ArrayList<ItemInfor> list;

    public ItemInforAdapter(Context context, ArrayList<ItemInfor> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemInforHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile,parent,false);
        ItemInforHolder holder = new ItemInforHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemInforHolder holder, @SuppressLint("RecyclerView") int position) {
        ItemInfor itemInfor = list.get(position);

        holder.txtTitle.setText(itemInfor.getTitle());
        holder.txtValue.setText(itemInfor.getValue());
        if (!itemInfor.isStatus()) {
            holder.txtValue.setCompoundDrawablesWithIntrinsicBounds(0,0,0, 0);
            holder.txtValue.setPadding(0, 0 , Config.dpToPx(10,context),0);
        }
        if (position == list.size()-1) holder.itemView.setPadding(0,0,0,0);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemInfor.isStatus()) {
                    if (position == 3) {
                        setBirth(holder.txtValue);
                        itemInfor.setValue(holder.txtValue.getText().toString());
                        return;
                    }
                    Intent intent = new Intent(context, EditInforActivity.class);
                    intent.putExtra("potision",position);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context,itemInfor.getTitle()+ " không được sửa",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setBirth(TextView txtValue) {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            calendar.setTime(spf.parse(txtValue.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                txtValue.setText(spf.format(calendar.getTime()));
            }
        };
        DatePickerDialog date = new DatePickerDialog(
                context,android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                callback,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        date.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        date.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ItemInforHolder extends RecyclerView.ViewHolder{

        TextView txtTitle,txtValue;

        public ItemInforHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtValue = itemView.findViewById(R.id.txtValue);
        }
    }
}
