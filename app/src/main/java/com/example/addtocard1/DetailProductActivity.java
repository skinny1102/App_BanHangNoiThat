package com.example.addtocard1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.bumptech.glide.Glide;
import com.example.addtocard1.Adapter.Photo;
import com.example.addtocard1.Adapter.PhotoSildeAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.relex.circleindicator.CircleIndicator;

public class DetailProductActivity extends AppCompatActivity {
    TextView tvName ,tvDes,tcPrice, tvCategories,imgAddtocart;
    ImageView imageView,imgBack,imgPlus,imgMinus;
    EditText edtQuantity;
    Product product;
    PhotoSildeAdapter photoSildeAdapter;
    ViewPager viewPager;
    CircleIndicator circleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_product);
        getSupportActionBar().hide();
        Intent i = getIntent();
        product = (Product) i.getSerializableExtra("obj_product");
            getSlide();
            getView();
            setView();
            setImgAddtocartOnclick();
            setClickQuantity();
            BackOnclick();
    }

    private void getSlide() {
        viewPager= findViewById(R.id.viewpager);
        circleIndicator = findViewById(R.id.circleindicator);
        photoSildeAdapter = new PhotoSildeAdapter(this,getlistPhoto());
        viewPager.setAdapter(photoSildeAdapter);
        circleIndicator.setViewPager(viewPager);
        photoSildeAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
    }

    private List<Photo> getlistPhoto() {

        List<Photo> list = new ArrayList<>();
        for (int j = 0; j < product.getListImgResource().size(); j++) {
            list.add(new Photo(product.getListImgResource().get(j)))  ;
        }
        System.out.println(list);
        return list ;
    }
    @SuppressLint("WrongViewCast")
    private void getView(){
        tvName = findViewById(R.id.tv_product_name);
        tvDes = findViewById(R.id.tv_des_product);
        imageView =findViewById(R.id.img_product);
        imgBack=findViewById(R.id.img_back);
        tcPrice= findViewById(R.id.tv_product_price);
        tvCategories=findViewById(R.id.tv_categories);
        imgPlus=findViewById(R.id.img_plus);
        imgMinus=findViewById(R.id.img_minus);
        edtQuantity=findViewById(R.id.edit_quantity);
        imgAddtocart =findViewById(R.id.img_add_to_cart);


    }
    private void setView(){
        Intent i = getIntent();
        Product product = (Product) i.getSerializableExtra("obj_product");
        tvName .setText(product.getNameProduct());
        tvCategories.setText(product.getCategories());
        tvDes.setText(product.getDescriptionProduct());
//        Integer price = product.getPriceProduct();
//        String str_price = price.toString().trim();

        tcPrice.setText(tiente(product.priceProduct)+" Vnđ");
    }
    private void setClickQuantity(){

        imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qt = edtQuantity.getText().toString();
                int quantity = Integer.parseInt(qt);
                String k = Integer.toString(quantity+1);

            edtQuantity.setText(k);
            }
        });
        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String qt = edtQuantity.getText().toString();
                int quantity = Integer.parseInt(qt);
                if(quantity!=0){
                    String k = Integer.toString(quantity-1);
                    edtQuantity.setText(k);
                }else {
                    return;
                }

            }
        });

    }
    private void setImgAddtocartOnclick(){
        imgAddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailProductActivity.this,"Thử chức năng click",Toast.LENGTH_SHORT).show();
            }
        });
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