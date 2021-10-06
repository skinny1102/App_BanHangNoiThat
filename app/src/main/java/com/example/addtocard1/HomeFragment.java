package com.example.addtocard1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.addtocard1.Cart.CartUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rcvProduct,rcvProduct1;
    private View mView;
    private MainActivity mainActivity;
    private List<Product> list, list1;
    private String USER_ID = "456";
    private ProductAdapter productAdapter;
    private ProductAdapter1 productAdapter1;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("product");
    DatabaseReference myRefCart = database.getReference("cart");
    DatabaseReference getMyRefCart = database.getReference("card");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        mainActivity = (MainActivity) getActivity();
        getCountProductCart();
        getRcv1();
        getRcv2();
        return mView;
    }
    private void getRcv1(){
        rcvProduct1 = mView.findViewById(R.id.rcv_product1);
        productAdapter1 = new ProductAdapter1(mainActivity);
        list1 = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mainActivity,3);
        rcvProduct1.setLayoutManager(gridLayoutManager);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(list1.size()>0){
                    list1.clear();
                }
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Product productObj = postSnapshot.getValue(Product.class);
                    list1.add(new Product(productObj.getIdProduct(),
                            productObj.getNameProduct(),
                            productObj.getDescriptionProduct(),
                            productObj.getPriceProduct(),
                            productObj.getQuantity(),
                            productObj.getImgResource(),
                            productObj.getCategories()));
                    productAdapter1.setData(list1, new ProductAdapter1.IClickAddToCartListener() {
                        @Override
                        public void onClickAddToCart( final ImageView imgAddToCart, Product product) {
                            AnimationUtil.translateAnimation(mainActivity.getViewAnimation(), imgAddToCart, mainActivity.getViewEndAnimation(), new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    product.setAddToCard(true);
                                    imgAddToCart.setBackgroundResource(R.drawable.bg_gray_conner_6);
                                    productAdapter1.notifyDataSetChanged();
                                    mainActivity.setCountProductCart(mainActivity.getmCountProduct()+1);
                                }
                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        rcvProduct1.setAdapter(productAdapter1);
    }
    private void getRcv2() {
        rcvProduct = mView.findViewById(R.id.rcv_product);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        rcvProduct.setLayoutManager(linearLayoutManager);
        productAdapter = new ProductAdapter(mainActivity);
        list = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(list.size()>0){
                    list.clear();
                }
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Product productObj = postSnapshot.getValue(Product.class);
                    list.add(new Product(productObj.getIdProduct(),
                            productObj.getNameProduct(),
                            productObj.getDescriptionProduct(),
                            productObj.getPriceProduct(),
                            productObj.getQuantity(),
                            productObj.getImgResource(),
                            productObj.getCategories()));
                    productAdapter.setData(list, new ProductAdapter.IClickAddToCartListener() {
                        @Override
                        public void onClickAddToCart(final ImageView imgAddToCart, Product product) {
                            AnimationUtil.translateAnimation(mainActivity.getViewAnimation(), imgAddToCart, mainActivity.getViewEndAnimation(), new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    product.setAddToCard(true);
                                    imgAddToCart.setBackgroundResource(R.drawable.bg_gray_conner_6);
                                    productAdapter.notifyDataSetChanged();
                                    mainActivity.setCountProductCart(mainActivity.getmCountProduct() + 1);

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                    }, new ProductAdapter.AddtoCartProduct() {
                        @Override
                        public void onClickAddToCartProduct(Product product) {
                         myRefCart.child(USER_ID).push().setValue(product);

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        rcvProduct.setAdapter(productAdapter);
    }
    public  void getCountProductCart(){

        myRefCart.orderByKey().equalTo(USER_ID).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    mainActivity.setCountProductCart((int) postSnapshot.getChildrenCount());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}


