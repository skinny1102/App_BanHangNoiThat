package com.example.addtocard1.Fragment;

import static java.lang.String.valueOf;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.example.addtocard1.Adapter.ProductCartAdapter;
import com.example.addtocard1.DetailProductActivity;
import com.example.addtocard1.MainActivity;
import com.example.addtocard1.Product;
import com.example.addtocard1.R;
import com.example.addtocard1.UserAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CardFragment extends Fragment {
    private String USER_ID = "456";
    private AHBottomNavigation ahBottomNavigation;
    private AHBottomNavigationViewPager ahBottomNavigationViewPager;
    MainActivity mainActivity;
    View view;
    ImageView imgBack;
    RecyclerView rcvCartProduct;
    ProductCartAdapter productCartAdapter;

    private List<Product> list;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefCart = database.getReference("cart");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainActivity = (MainActivity) getActivity();
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_card, container, false);
        getActionBackClick();
        getCartProduct();
        return view;
    }
    private void getCartProduct(){
        String str = "https://storage.googleapis.com/download/storage/v1/b/appdecornoithat.appspot.com/o/476403642.jpg?generation=1634020992946068&alt=media";
        rcvCartProduct = view.findViewById(R.id.rcv_cart_product_list);
        productCartAdapter = new ProductCartAdapter(mainActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        rcvCartProduct.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
//        list.add(new Product("hihi", "Haha","Mô tả",  10000, 1, str,"Sản phẩm",null));
//        productCartAdapter.setData(list);
        myRefCart.child(USER_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(list.size()>0){
                    list.clear();
                }
                for (DataSnapshot dataSnapshot1:snapshot.getChildren()) {

                Product productObj= dataSnapshot1.getValue(Product.class);
                    list.add(new Product(productObj.getIdProduct(),
                            productObj.getNameProduct(),
                            productObj.getDescriptionProduct(),
                            productObj.getPriceProduct(),
                            productObj.getQuantity(),
                            productObj.getImgResource(),
                            productObj.getCategories(),
                            productObj.getListImgResource()));
                        productCartAdapter.setData(list, new ProductCartAdapter.IClickRemoveProductCartListener() {
                            @Override
                            public void onClickRemoveProductCart(Product product) {
                             myRefCart.child(USER_ID).child(product.idProduct).removeValue();


                            }
                        });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        rcvCartProduct.setAdapter(productCartAdapter);

    }
    private void getActionBackClick() {
        imgBack=view.findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ahBottomNavigation=mainActivity.findViewById(R.id.AHBottomNavigation);
                ahBottomNavigationViewPager =mainActivity.findViewById(R.id.AHBottomNavigationViewPager);
                ahBottomNavigation.setCurrentItem(0);
                ahBottomNavigationViewPager.setCurrentItem(0);
            }
        });
    }
    private void loadlai(){
        ahBottomNavigation=mainActivity.findViewById(R.id.AHBottomNavigation);
        ahBottomNavigationViewPager =mainActivity.findViewById(R.id.AHBottomNavigationViewPager);
        ahBottomNavigation.setCurrentItem(0);
        ahBottomNavigationViewPager.setCurrentItem(0);
        ahBottomNavigation.setCurrentItem(1);
        ahBottomNavigationViewPager.setCurrentItem(1);
    }

}