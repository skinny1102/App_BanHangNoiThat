package com.example.addtocard1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.addtocard1.Adapter.Photo;
import com.example.addtocard1.Adapter.PhotoSildeAdapter;
import com.example.addtocard1.Adapter.ProductAdapter;
import com.example.addtocard1.Adapter.ProductlienquanAdapter;
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
    RecyclerView recyclerView;
    public String USER_ID ;
    MainActivity mainActivity;
    PhotoSildeAdapter photoSildeAdapter;
    private ProductlienquanAdapter adapter;
    private List<Product> listpr;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    String Categories;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefCart = database.getReference("cart");
    DatabaseReference myRefProduct= database.getReference("product");

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
            getsplq();
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
//                Intent i = new Intent(DetailProductActivity.this, MainActivity.class);
//                DetailProductActivity.this.finish(); //Use your current activity
//                startActivity(i);
                finish();
            }
        });

    }
    private void getsplq() {
        recyclerView = findViewById(R.id.rcvpr);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ProductlienquanAdapter(this);
        listpr = new ArrayList<>();
        myRefProduct.orderByChild("categories").equalTo(product.getCategories()).limitToLast(3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listpr.size()>0){
                    listpr.clear();
                }
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Product productObj = postSnapshot.getValue(Product.class);
                   if (productObj.getIdProduct().trim() != product.getIdProduct().trim()){
                       System.out.println("nó vào đây");
                       listpr.add(new Product(productObj.getIdProduct(),
                               productObj.getNameProduct(),
                               productObj.getDescriptionProduct(),
                               productObj.getPriceProduct(),
                               productObj.getQuantity(),
                               productObj.getImgResource(),
                               productObj.getCategories(),
                               productObj.getListImgResource()));
                       adapter.setData(listpr);
                   }else {
                       System.out.println("nó vào else");
                       return;
                   }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setAdapter(adapter);
    }
    public void showDetailProduct(Product product){
        Toast.makeText(DetailProductActivity.this,"Chưa cập nhật chức năng này ", Toast.LENGTH_LONG).show();
//        Intent myIntent = new Intent(mainActivity, DetailProductActivity.class);
//        myIntent.putExtra("obj_product",product);
//        myIntent.putExtra("USER_ID",mainActivity.getG_uid());
//        mainActivity.startActivity(myIntent);


    }
}