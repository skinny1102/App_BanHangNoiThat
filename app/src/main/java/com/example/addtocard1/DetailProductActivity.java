package com.example.addtocard1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailProductActivity extends AppCompatActivity {
    TextView tvName ,tvDes,tcPrice, tvCategories;
    ImageView imageView,imgBack;
    Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        getSupportActionBar().hide();
            getView();
            setView();
            BackOnclick();

    }
    private void getView(){
        tvName = findViewById(R.id.tv_product_name);
        tvDes = findViewById(R.id.tv_des_product);
        imageView =findViewById(R.id.img_product);
        imgBack=findViewById(R.id.img_back);
        tcPrice= findViewById(R.id.tv_product_price);
        tvCategories=findViewById(R.id.tv_categories);

    }
    private void setView(){
        Intent i = getIntent();
        Product product = (Product) i.getSerializableExtra("obj_product");
        Glide.with(this).load(product.imgResource).into(imageView);
        tvName .setText(product.getNameProduct());
        tvCategories.setText(product.getCategories());
        tvDes.setText(product.getDescriptionProduct());
//        Integer price = product.getPriceProduct();
//        String str_price = price.toString().trim();

        tcPrice.setText(tiente(product.priceProduct)+" Vnđ");
    }

    private String  tiente(int price){
//        long vnd = price;
        // tạo 1 NumberFormat để định dạng tiền tệ theo tiêu chuẩn của Việt Nam
        // đơn vị tiền tệ của Việt Nam là đồng
//        Locale localeVN = new Locale("vi", "VN");
//        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
//        String str1 = currencyVN.format(vnd);
        String str1 = NumberFormat.getIntegerInstance().format(price);
        return str1;
    }

    private void BackOnclick(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailProductActivity.this, MainActivity.class);
                DetailProductActivity.this.finish(); //Use your current activity
                startActivity(i);
            }
        });
    }
}