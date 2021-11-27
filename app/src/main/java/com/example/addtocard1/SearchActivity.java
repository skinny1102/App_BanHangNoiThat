package com.example.addtocard1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.addtocard1.Adapter.ProductlienquanAdapter;
import com.example.addtocard1.Doituong.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    String strSearch;
    TextView tvkeyword;
    RecyclerView recyclerView;
    private ProductlienquanAdapter adapter;
    private List<Product> listpr;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefProduct= database.getReference("product");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        BackOnclick();
        Intent i = getIntent();
        strSearch = i.getStringExtra("strsearh");
        tvkeyword = findViewById(R.id.tv_tukhoa);
        tvkeyword.setText(strSearch);
        getitemSearch();
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
    private void getitemSearch(){
//        endAt(strSearch+"\uf8ff")
        recyclerView = findViewById(R.id.rcv_searchproduct);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ProductlienquanAdapter(this);
        listpr = new ArrayList<>();
        myRefProduct.orderByChild("nameProduct").startAt(strSearch).limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listpr.size()>0){
                    listpr.clear();
                }
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Product productObj = postSnapshot.getValue(Product.class);
                             listpr.add(new Product(productObj.getIdProduct(),
                                productObj.getNameProduct(),
                                productObj.getDescriptionProduct(),
                                productObj.getPriceProduct(),
                                productObj.getQuantity(),
                                productObj.getImgResource(),
                                productObj.getCategories(),
                                productObj.getListImgResource()));
                              adapter.setData(listpr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setAdapter(adapter);
    }
}