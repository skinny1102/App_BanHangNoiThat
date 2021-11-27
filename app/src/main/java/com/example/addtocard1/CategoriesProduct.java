package com.example.addtocard1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.addtocard1.Adapter.ProductAdapter;
import com.example.addtocard1.Animation.AnimationUtil;
import com.example.addtocard1.Doituong.Product;
import com.example.addtocard1.my_Interface.IClickProuductListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoriesProduct extends AppCompatActivity {
    String Categories;
    RecyclerView rcvProduct;
    ProductAdapter adapter;
    List<Product> list;
    MainActivity mainActivity;
    String USER_ID;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefProduct = database.getReference("product");
    DatabaseReference myRefCart = database.getReference("cart");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_product);
        getSupportActionBar().hide();
        Intent i = getIntent();
        Categories = i.getStringExtra("Categories");
        USER_ID = i.getStringExtra("USER_ID");
        getRcv2();
        BackOnclick();

    }
    private void BackOnclick(){
        ImageView imgBack= findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

    }
    private void getRcv2() {

        rcvProduct = findViewById(R.id.rcv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvProduct.setLayoutManager(linearLayoutManager);
        adapter = new ProductAdapter(this);
        list = new ArrayList<>();
        myRefProduct.orderByChild("categories").equalTo(Categories).limitToLast(50).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(list.size()>0){
                    list.clear();
                } if (snapshot.getValue()==null){
                    TextView chuacosp = findViewById(R.id.tvchuacosanpham);
                    chuacosp.setVisibility(View.VISIBLE);
//                    Toast.makeText(CategoriesProduct.this,"Không có sản phẩm",Toast.LENGTH_LONG).show();
                }else {
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
                        adapter.setData(list, new ProductAdapter.IClickAddToCartListener() {
                            @Override
                            public void onClickAddToCart(ImageView imgAddToCart, Product product) {

                            }
                        }, new ProductAdapter.AddtoCartProduct() {
                            @Override
                            public void onClickAddToCartProduct(Product product) {

                                product.setQuantity(1);
                                myRefCart.child(USER_ID).child(product.idProduct).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(CategoriesProduct.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_LONG).show();
                                    }
                                });
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        rcvProduct.setAdapter(adapter);
    }

    public void showDetailProduct(Product product){
        Intent myIntent = new Intent(this, DetailProductActivity.class);
        myIntent.putExtra("obj_product",product);
        myIntent.putExtra("USER_ID",USER_ID);
        startActivity(myIntent);
    }
}