package com.example.addtocard1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.addtocard1.Adapter.Photo;
import com.example.addtocard1.Adapter.PhotoSildeAdapter;
import com.example.addtocard1.Doituong.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class DetailProductActivity extends AppCompatActivity {
    TextView tvName ,tvDes,tcPrice, tvCategories,imgAddtocart;
    ImageView imageView,imgBack,imgPlus,imgMinus;
    EditText edtQuantity;
    Product product;
    public String USER_ID ;
    MainActivity mainActivity;
    PhotoSildeAdapter photoSildeAdapter;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    String Categories;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefCart = database.getReference("cart");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_product);
        getSupportActionBar().hide();
        Intent i = getIntent();
        product = (Product) i.getSerializableExtra("obj_product");
        USER_ID = (String) i.getSerializableExtra("USER_ID");
    System.out.println("ID là"+USER_ID);
        Categories=product.getCategories();
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
                String quantity = edtQuantity.getText().toString().trim();
                int soluong = Integer.parseInt(quantity);
                 if(soluong==0){
                     Toast.makeText(DetailProductActivity.this,"Số lượng không phù hợp", Toast.LENGTH_LONG).show();
                 }else {

                     product.setQuantity(soluong);
                     myRefCart.child(USER_ID).child(product.idProduct).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             Toast.makeText(DetailProductActivity.this,"Thêm thành công", Toast.LENGTH_LONG).show();
                         }
                     });
                 }
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