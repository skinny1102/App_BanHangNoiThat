package com.example.addtocard1.Fragment;

import static java.lang.String.valueOf;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.example.addtocard1.Adapter.ProductCartAdapter;
import com.example.addtocard1.DonHangActivity;
import com.example.addtocard1.MainActivity;
import com.example.addtocard1.Doituong.Product;
import com.example.addtocard1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CardFragment extends Fragment {

    private AHBottomNavigation ahBottomNavigation;
    private AHBottomNavigationViewPager ahBottomNavigationViewPager;
    MainActivity mainActivity;
    View view;
    TextView tvDelete,tvTongtien,tvCheckAll,tvDatHang,checktrong ;
    ImageView imgBack;
    RecyclerView rcvCartProduct;
    ProductCartAdapter productCartAdapter;
    CheckBox checkBox,checkboxAll;
    int tongtien;
    public String USER_ID;
    private List<Product> list;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefCart = database.getReference("cart");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainActivity = (MainActivity) getActivity();
        USER_ID = mainActivity.getG_uid();
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_card, container, false);
        getview();
        getActionBackClick();
        deleteChecklist();
        getCartProduct();
        DatHang();
        checkedAll();
        return view;
    }
    private void getview(){
        checkboxAll = view.findViewById(R.id.checkboxAll);
        tvTongtien=view.findViewById(R.id.tv_tongtien);
         checktrong = view.findViewById(R.id.checktrong);
    }
    private void getCartProduct(){
        rcvCartProduct = view.findViewById(R.id.rcv_cart_product_list);
        productCartAdapter = new ProductCartAdapter(mainActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        rcvCartProduct.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        myRefCart.child(USER_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(list.size()>0){
                    list.clear();
                }
                if(snapshot.getValue()==null){
//                    checktrong.setVisibility(View.VISIBLE);
//                    checkboxAll.setVisibility(View.GONE);
//                    loadlai();
                    tvTongtien.setText(0 +"vnđ");
                }
                tongtien=0;
                if(snapshot.getValue()!=null){
//                    checktrong.setVisibility(View.GONE);
                    //checkboxAll.setVisibility(View.VISIBLE);
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
                        tongtien += (productObj.getPriceProduct()*productObj.getQuantity());
                        tvTongtien.setText(tiente(tongtien)+"vnđ");
                        productCartAdapter.setData(list, new ProductCartAdapter.IClickRemoveProductCartListener() {
                            @Override
                            public void onClickRemoveProductCart(Product product) {
                                myRefCart.child(USER_ID).child(product.idProduct).removeValue();
                            }
                        });
                    }
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
        ahBottomNavigation.setNotification("", 1);
        ahBottomNavigation.setCurrentItem(0);
        ahBottomNavigationViewPager.setCurrentItem(0);
        tvTongtien.setText(0+"vnđ");

    }
    public void checkedAll(){

        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkboxAll.isChecked()){
                    productCartAdapter.selectAll();
                }
                else {
                    productCartAdapter.unselectall();
                }


            }
        });
    }
    private void deleteChecklist(){
        tvDelete= view.findViewById(R.id.tv_delete);
        tvDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            List<String> prlistdelte = productCartAdapter.getListid();
            System.out.println(prlistdelte);
            if (prlistdelte.size()==0){
                Toast.makeText(mainActivity,"Bạn chưa chọn sản phẩm để xóa",Toast.LENGTH_SHORT).show();
            }
            else {
                for (String id : prlistdelte) {
                    myRefCart.child(USER_ID).child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(mainActivity,"Xóa thành công",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            productCartAdapter.notifyDataSetChanged();
        }
    });
}
    private String  tiente(int price){
        String str1 = NumberFormat.getIntegerInstance().format(price);
        return str1;
    }
    private void DatHang(){
        tvDatHang = view.findViewById(R.id.img_dathang);
        tvDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.size()==0 || list==null){
                    Toast.makeText(mainActivity,"Giỏ hàng đang trống",Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(mainActivity , DonHangActivity.class);

                     intent.putExtra("list_product",(Serializable) list);
                    intent.putExtra("tongtien",tongtien);
                    intent.putExtra("USER_ID",mainActivity.getG_uid());
                    startActivity(intent);
                }

            }
        });

    }
}