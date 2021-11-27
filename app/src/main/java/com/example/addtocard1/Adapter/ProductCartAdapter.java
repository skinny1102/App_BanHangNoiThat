package com.example.addtocard1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.bumptech.glide.Glide;
import com.example.addtocard1.MainActivity;
import com.example.addtocard1.Doituong.Product;
import com.example.addtocard1.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductCartAdapter extends RecyclerView.Adapter<ProductCartAdapter.ProductCartViewHolder> {
    private List<Product> mListProduct;
    private Context context;
    MainActivity mainActivity;
    private AHBottomNavigation ahBottomNavigation;
    private AHBottomNavigationViewPager ahBottomNavigationViewPager;
    IClickRemoveProductCartListener iClickRemoveProductCartListener;
    public List<String> listid = new ArrayList<>();
    public  boolean isSelectedAll;
    public  void selectAll(){
        isSelectedAll=true;
        notifyDataSetChanged();
    }
    public void unselectall(){
        isSelectedAll=false;
        notifyDataSetChanged();
    }

    public String USER_ID;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefCart = database.getReference("cart");

    public List<String> getListid() {
        return listid;
    }
    public int tongtien;

    public int getTongtien() {
        return tongtien;
    }

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
        USER_ID = mainActivity.getG_uid();
        return new ProductCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCartViewHolder holder, int position) {
        Product product = mListProduct.get(position);
        tongtien +=product.getPriceProduct();

        if(product == null){
//            loadlai();
            notifyDataSetChanged();
            return;
        }else{

            Glide.with(context).load(product.getImgResource()).into(holder.imgProduct);
            holder.tvNameProduct.setText(product.getNameProduct());

            holder.edtQuantity.setText(""+product.getQuantity());
            holder.tvPriceProduct.setText(tiente(product.getPriceProduct()) +" vnđ");

            holder.checkBox.setTag(product.idProduct);

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        if (holder.checkBox.isChecked()) {
                            String k=  holder.checkBox.getTag().toString();
                            listid.add(k);
                            System.out.println(listid);

                        }else {
                            String k=  holder.checkBox.getTag().toString();
                            listid.remove(k);

                        }
                }
            });

             if (!isSelectedAll){
                holder.checkBox.setChecked(false);
                 String k=  holder.checkBox.getTag().toString();
                 listid.remove(k);
            }
            else  {
                holder.checkBox.setChecked(true);
                 String k=  holder.checkBox.getTag().toString();
                 listid.add(k);
                 System.out.println(listid);
            };

            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickRemoveProductCartListener.onClickRemoveProductCart(product);
                }
            });
        }
        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityplus= product.getQuantity()+1;
                myRefCart.child(USER_ID).child(product.getIdProduct()+"/quantity").setValue(quantityplus);
            }
        });
        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityminus= product.getQuantity()-1;
                if(quantityminus<1){
                    Toast.makeText(mainActivity,"Số lượng sản phẩm không hợp lệ",Toast.LENGTH_SHORT).show();
                }else {
                    myRefCart.child(USER_ID).child(product.getIdProduct()+"/quantity").setValue(quantityminus);
                }

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
        ImageView imgProduct,imgMinus,imgPlus;
        TextView tvNameProduct,tvPriceProduct;
        Button btnDelete;
        EditText edtQuantity;
        public ProductCartViewHolder(@NonNull View itemView) {
            super(itemView);
                checkBox =  itemView.findViewById(R.id.checkbox);
                imgProduct = itemView.findViewById(R.id.img_listproduct);
                tvNameProduct=itemView.findViewById(R.id.tv_product_name);
                tvPriceProduct=itemView.findViewById(R.id.tv_product_price);
                btnDelete = itemView.findViewById(R.id.btn_Delete);
                edtQuantity=itemView.findViewById(R.id.edit_quantity);
                imgMinus=itemView.findViewById(R.id.img_minus);
                imgPlus=itemView.findViewById(R.id.img_plus);

        }
    }
    private String  tiente(int price){
        String str1 = NumberFormat.getIntegerInstance().format(price);
        return str1;
    }
//    public void selectAll() {
//        int count = rv.getChildCount();
//        for (int i = 0; i < count; i++) {
//            View view = rv.getChildAt(i);
//            if (!view.isActivated()) {
//                view.setActivated(true);
//                selection.add(view);
//            }
//        }
//    }


}
