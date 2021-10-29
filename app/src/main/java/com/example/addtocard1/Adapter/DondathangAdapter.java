package com.example.addtocard1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.addtocard1.AllDonHangActivity;
import com.example.addtocard1.ChitietDonhangActivity;
import com.example.addtocard1.Doituong.DonHang;
import com.example.addtocard1.MainActivity;
import com.example.addtocard1.R;

import java.util.List;

public class DondathangAdapter extends RecyclerView.Adapter<DondathangAdapter.DondathangViewHolder> {
    private List<DonHang> mlist;
    private Context context;
    public void setData(List<DonHang> list){
       this.mlist=list;
       notifyDataSetChanged();
    }

    public DondathangAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DondathangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang,parent,false);
        return new DondathangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DondathangViewHolder holder, int position) {
        DonHang donHang = mlist.get(position);
        if(donHang==null){
            return;
        }else {
            holder.tvmadathang.setText(donHang.getMaDonhang());
            holder.tvngaydathang.setText(donHang.getDateDatHang());
           if (donHang.isTrangThai()==false){
               holder.tvTrangthai.setText("Chưa xác nhân");
           }else {
               holder.tvTrangthai.setText("Đã xác nhân");
           }
           holder.imgcardview.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(context, ChitietDonhangActivity.class);
                   intent.putExtra("maDonhang",donHang.getMaDonhang());
                   context.startActivity(intent);
               }
           });
        }
    }

    @Override
    public int getItemCount() {
        if(mlist!=null){
            return  mlist.size();
        }
        return 0;
    }

    public class DondathangViewHolder extends RecyclerView.ViewHolder {
        TextView tvmadathang , tvngaydathang,tvTrangthai;
        CardView imgcardview;
        public DondathangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvmadathang = itemView.findViewById(R.id.tv_madonhang);
            tvngaydathang= itemView.findViewById(R.id.tv_ngaydathang);
            tvTrangthai = itemView.findViewById(R.id.tv_trangthai);
            imgcardview=itemView.findViewById(R.id.img_cardview);
        }
    }
}
