package com.example.addtocard1.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.GridView;
import android.widget.ImageView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.example.addtocard1.Adapter.Categories;
import com.example.addtocard1.Adapter.CategoriesAdapter;
import com.example.addtocard1.Animation.AnimationUtil;

import com.example.addtocard1.DetailProductActivity;
import com.example.addtocard1.MainActivity;
import com.example.addtocard1.Doituong.Product;
import com.example.addtocard1.Adapter.ProductAdapter;
import com.example.addtocard1.Adapter.ProductAdapter1;
import com.example.addtocard1.R;
import com.example.addtocard1.my_Interface.IClickProuductListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rcvProduct,rcvProduct1,rcvCategories;
    private View mView;
    private MainActivity mainActivity;
    private List<Product> list, list1;
    public String USER_ID ;
    private ProductAdapter productAdapter;
    private ProductAdapter1 productAdapter1;
    private AHBottomNavigationViewPager ahBottomNavigationViewPager;
    ArrayList<Categories> arrayListCategories;
    GridView gridView;
    CategoriesAdapter categoriesAdapter;

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
         USER_ID  = mainActivity.getG_uid();
        getCountProductCart();
        getRcv1();
        getRcv2();
        getViewCategories();
        return mView;
    }
    private void getRcv1(){

        rcvProduct1 = mView.findViewById(R.id.rcv_product1);
        productAdapter1 = new ProductAdapter1(mainActivity);
        list1 = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mainActivity,3);
        rcvProduct1.setLayoutManager(gridLayoutManager);
        myRef.limitToLast(3).addValueEventListener(new ValueEventListener() {
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
                            productObj.getCategories(),
                            productObj.getListImgResource()));
                    productAdapter1.setData(list1, new ProductAdapter1.IClickAddToCartListener() {
                        @Override
                        public void onClickAddToCart(final ImageView imgAddToCart, Product product) {
                            AnimationUtil.translateAnimation(mainActivity.getViewAnimation(), imgAddToCart, mainActivity.getViewEndAnimation(), new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
//                                    product.setAddToCard(true);
//                                    imgAddToCart.setBackgroundResource(R.drawable.bg_gray_conner_6);
//                                    productAdapter1.notifyDataSetChanged();
//                                    mainActivity.setCountProductCart(mainActivity.getmCountProduct()+1);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                    }, new ProductAdapter.AddtoCartProduct() {
                        @Override
                        public void onClickAddToCartProduct(Product product) {
                            product.setQuantity(1);
                            myRefCart.child(USER_ID).child(product.idProduct).setValue(product);
                        }
                    }, new IClickProuductListener() {
                        @Override
                        public void onClickItemProduct(Product product) {
                            showDetailProduct(product);
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
        myRef.limitToLast(4).addValueEventListener(new ValueEventListener() {
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
                            productObj.getCategories(),
                            productObj.getListImgResource()));
                    productAdapter.setData(list, new ProductAdapter.IClickAddToCartListener() {
                        @Override
                        public void onClickAddToCart(final ImageView imgAddToCart, Product product) {
                            AnimationUtil.translateAnimation(mainActivity.getViewAnimation(), imgAddToCart, mainActivity.getViewEndAnimation(), new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
//                                    product.setAddToCard(true);
//                                   imgAddToCart.setBackgroundResource(R.drawable.bg_gray_conner_6);
                                    productAdapter.notifyDataSetChanged();
//                                  mainActivity.setCountProductCart(mainActivity.getmCountProduct() + 1);




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

                            product.setQuantity(1);
                            myRefCart.child(USER_ID).child(product.idProduct).setValue(product);
//

//
                        }
                    }, new IClickProuductListener() {
                        /// onlick item show itemproduct details
                        @Override
                        public void onClickItemProduct(Product product) {
                            showDetailProduct(product);
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
                if(snapshot.getValue()==null){
                    mainActivity.setCountProductCart(0);
                }else {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        mainActivity.setCountProductCart((int) postSnapshot.getChildrenCount());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void showDetailProduct(Product product){
         Intent myIntent = new Intent(mainActivity, DetailProductActivity.class);
         myIntent.putExtra("obj_product",product);
         myIntent.putExtra("USER_ID",mainActivity.getG_uid());
         mainActivity.startActivity(myIntent);


    }
    public void getViewCategories(){
        rcvCategories =mView.findViewById(R.id.rcv_list_categories);
        categoriesAdapter = new CategoriesAdapter(mainActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( mainActivity,RecyclerView.HORIZONTAL,false);
        rcvCategories.setLayoutManager(linearLayoutManager);
        categoriesAdapter.setData(getListCategories());
         rcvCategories.setAdapter(categoriesAdapter);
    }

    private List<Categories> getListCategories() {
        List<Categories> list = new ArrayList<>();
        list.add(new Categories("Giường",R.drawable.bead_categories));
        list.add(new Categories("Ghế",R.drawable.chair_categories));
        list.add(new Categories("Bàn ăn",R.drawable.table_categories));
        list.add(new Categories("Sofa",R.drawable.sofa_categories));
        list.add(new Categories("Tủ",R.drawable.tuquanao_categories));
        list.add(new Categories("Kệ",R.drawable.kegia_categories));
        return list;
    }


}


