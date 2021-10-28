package com.example.addtocard1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.addtocard1.Doituong.Product;
import com.example.addtocard1.R;

import java.text.NumberFormat;
import java.util.List;

public class ProductDonDatHangAdapter extends RecyclerView.Adapter<ProductDonDatHangAdapter.ProductDonDatHangViewHolder> {

    private List<Product> mListProduct;
    private Context context;
    public void setData(List<Product> list ){
        this.mListProduct = list;
        notifyDataSetChanged();
    }

    public ProductDonDatHangAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ProductDonDatHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dondathang_product,parent,false);
        return new ProductDonDatHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDonDatHangViewHolder holder, int position) {
        Product product = mListProduct.get(position);
        if (product==null){
            return;
        }else {
            Glide.with(context).load(product.getImgResource()).into(holder.imgProduct);
            holder.tvNameProduct.setText(product.getNameProduct());
            holder.tvQuantity.setText(""+product.getQuantity());
            holder.tvPriceProduct.setText(tiente(product.getPriceProduct()) +" vnđ");
        }

    }

    @Override
    public int getItemCount() {
        if(mListProduct!=null){
            return  mListProduct.size();
        }
        return 0;
    }

    public  class  ProductDonDatHangViewHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct;
        TextView tvNameProduct , tvPriceProduct , tvQuantity;

        public ProductDonDatHangViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_dondathangproduct);
            tvNameProduct = itemView.findViewById(R.id.tv_product_name);
            tvPriceProduct = itemView.findViewById(R.id.tv_product_price);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
        }
    }
    private String  tiente(int price){
        String str1 = NumberFormat.getIntegerInstance().format(price);
        return str1;
    }
}
