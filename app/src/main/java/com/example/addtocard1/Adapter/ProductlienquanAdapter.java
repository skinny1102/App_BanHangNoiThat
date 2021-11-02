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

public class ProductlienquanAdapter extends RecyclerView.Adapter<ProductlienquanAdapter.productlienquanViewHolder> {
    private List<Product> mListProduct;
    private Context context;
    public  void setData(List<Product> list){
        this.mListProduct = list;
        notifyDataSetChanged();
    }

    public ProductlienquanAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public productlienquanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lienquan,parent,false);
        return new productlienquanViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull productlienquanViewHolder holder, int position) {
        Product product = mListProduct.get(position);
        if(product == null){
//            return ;
            notifyDataSetChanged();
            return;
        }

        Glide.with(context).load(product.getImgResource()).into(holder.imProduct);
        holder.tvProductName.setText(product.getNameProduct());
        holder.tvDescription.setText(NumberFormat.getIntegerInstance().format(product.getPriceProduct()) + " vnđ");
    }

    @Override
    public int getItemCount() {
        if(mListProduct!=null){
            return  mListProduct.size();
        }
        return 0;
    }

    public  class productlienquanViewHolder extends RecyclerView.ViewHolder {
        private ImageView imProduct;
        private TextView tvProductName , tvDescription;
        public productlienquanViewHolder(@NonNull View itemView) {
            super(itemView);
            imProduct = (ImageView) itemView.findViewById(R.id.img_product);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
        }
    }
}
