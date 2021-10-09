package com.example.addtocard1.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.example.addtocard1.MainActivity;
import com.example.addtocard1.Product;
import com.example.addtocard1.R;
import com.example.addtocard1.my_Interface.interfaceProduct;

public class DetailProductFragment extends Fragment implements interfaceProduct {
    public View view;
    Product product;
    private MainActivity mainActivity;
    private AHBottomNavigationViewPager ahBottomNavigationViewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detailproduct, container, false);
        mainActivity = (MainActivity) getActivity();
        // Inflate the layout for this fragment
        setImgBack();

            Product product = (Product) getArguments().get("obj_product");
            System.out.println(product);
//        String value = (String) getArguments().get("string");
        TextView tv = view.findViewById(R.id.tv_product_name);
        tv.setText(product.getNameProduct());


        return view;
    }

    @Override
    public void iProduct(Product product) {

    }
    public  void setImgBack(){
        ImageView imgback = view.findViewById(R.id.img_back);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ahBottomNavigationViewPager= mainActivity.findViewById(R.id.AHBottomNavigationViewPager);
                HomeFragment homeFragment = new HomeFragment();

                FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_detail,homeFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}