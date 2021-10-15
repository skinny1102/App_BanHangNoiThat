package com.example.addtocard1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.addtocard1.MainActivity;
import com.example.addtocard1.Product;
import com.example.addtocard1.R;

import java.text.NumberFormat;
import java.util.List;

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ProductCartViewHolder> {
    private List<Product> mListProduct;
    private Context context;
    MainActivity mainActivity;
    IClickRemoveProductCartListener iClickRemoveProductCartListener;
    public ProductCartAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<Product> list,IClickRemoveProductCartListener iClickRemoveProductCartListener){
        this.mListProduct = list;
        this.iClickRemoveProductCartListener=iClickRemoveProductCartListener;
        notifyDataSetChanged();
    }
    public interface IClickRemoveProductCartListener{
        void  onClickRemoveProductCart(Product product);
    }
    @NonNull
    @Override
    public ProductCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listproductcart,parent,false);
        mainActivity = (MainActivity) parent.getContext();
        return new ProductCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCartViewHolder holder, int position) {
        Product product = mListProduct.get(position);
        if(product == null){
            return;
        }
        Glide.with(context).load(product.getImgResource()).into(holder.imgProduct);
        holder.tvNameProduct.setText(product.getNameProduct());
        holder.tvPriceProduct.setText(tiente(product.getPriceProduct()) +" vnđ");
        holder.checkBox.setTag(position);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickRemoveProductCartListener.onClickRemoveProductCart(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mListProduct!=null){
            return  mListProduct.size();
        }
        return 0;
    }

    public class ProductCartViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;
        ImageView imgProduct;
        TextView tvNameProduct,tvPriceProduct;
        Button btnDelete;
        public ProductCartViewHolder(@NonNull View itemView) {
            super(itemView);
                checkBox =  itemView.findViewById(R.id.checkbox);
                imgProduct = itemView.findViewById(R.id.img_listproduct);
                tvNameProduct=itemView.findViewById(R.id.tv_product_name);
                tvPriceProduct=itemView.findViewById(R.id.tv_product_price);
                btnDelete = itemView.findViewById(R.id.btn_Delete);
        }
    }
    private String  tiente(int price){
        String str1 = NumberFormat.getIntegerInstance().format(price);
        return str1;
    }
}
