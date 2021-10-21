package com.example.addtocard1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.bumptech.glide.Glide;
import com.example.addtocard1.MainActivity;
import com.example.addtocard1.Product;
import com.example.addtocard1.R;
import com.example.addtocard1.my_Interface.IClickProuductListener;

import java.text.NumberFormat;
import java.util.List;

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private List<Product> mListProduct;
    private IClickAddToCartListener iClickAddToCartListener;
    private AddtoCartProduct addToCartListener ;
    private Context context;
    private MainActivity mainActivity;

    public ProductAdapter(Context context) {
        this.context = context;
    }
    private IClickProuductListener iClickProuductListener;

    public  void setData(List<Product> list , IClickAddToCartListener listener, AddtoCartProduct addToCartListener,
                         IClickProuductListener iClickProuductListener){
        this.mListProduct = list;
        this.iClickAddToCartListener = listener;
        this.addToCartListener=addToCartListener;
        this.iClickProuductListener=iClickProuductListener;
        notifyDataSetChanged();
    }
    public interface IClickAddToCartListener{
        void  onClickAddToCart(ImageView imgAddToCart,Product product);
    }
    public interface AddtoCartProduct{
        void  onClickAddToCartProduct(Product product);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        mainActivity = (MainActivity) parent.getContext();
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {
        Product product = mListProduct.get(position);
        if(product == null){
//            return ;
            notifyDataSetChanged();
            return;
        }

        Glide.with(context).load(product.imgResource).into(holder.imProduct);
        holder.tvProductName.setText(product.getNameProduct());
        holder.tvDescription.setText(NumberFormat.getIntegerInstance().format(product.getPriceProduct()) + " vnđ");
//        if(product.isAddToCard()){
//            holder.imgAddToCard.setBackgroundResource(R.drawable.bg_gray_conner_6);
//
//        }else{
//            holder.imgAddToCard.setBackgroundResource(R.drawable.bg_red_conner_6);
//        }
        holder.imgAddToCard.setBackgroundResource(R.drawable.bg_red_conner_6);
        holder.imgAddToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickAddToCartListener.onClickAddToCart(holder.imgAddToCard,product);
                addToCartListener.onClickAddToCartProduct(product);
//                if(!product.isAddToCard()){
//
//
//                }

            }
        });
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickProuductListener.onClickItemProduct(product);
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



    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private ImageView imProduct;
        private TextView tvProductName , tvDescription;
        private  ImageView imgAddToCard;
        private CardView layoutItem;
        private AHBottomNavigationViewPager ahBottomNavigationViewPager;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imProduct = (ImageView) itemView.findViewById(R.id.img_product);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgAddToCard =  itemView.findViewById(R.id.img_add_to_card);
            layoutItem = itemView.findViewById(R.id.layout_item_product_list);
        }
    }
}
