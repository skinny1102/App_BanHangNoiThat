package com.example.addtocard1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.addtocard1.Doituong.User;
import com.example.addtocard1.Fragment.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.huawei.agconnect.appmessaging.AGConnectAppMessaging;


public class MainActivity extends AppCompatActivity  {
    private AHBottomNavigation ahBottomNavigation;
    private AHBottomNavigationViewPager ahBottomNavigationViewPager;
    private  ViewPagerAdapter adapter;
    View viewEndAnimation;
    ImageView viewAnimation;
    User g_User;
    String phoneNumber;
    public String G_uid;

    public String getG_uid() {
        return G_uid;
    }

    public void setG_uid(String g_uid) {
        G_uid = g_uid;
    }

    private int mCountProduct;
    HomeFragment homeFragment;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRefCart = database.getReference("cart");
    DatabaseReference myRefProfile = database.getReference("profile");
   private boolean flag = false;


    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getUserInformation();
//        AGConnectAppMessaging.getInstance().setFetchMessageEnable( true );
//
//        AGConnectAppMessaging appMessaging = AGConnectAppMessaging.getInstance();
//        appMessaging.setDisplayEnable(false);
        viewEndAnimation = findViewById(R.id.view_end_animation);
        viewAnimation = findViewById(R.id.view_animation);

        ahBottomNavigation=findViewById(R.id.AHBottomNavigation);
        ahBottomNavigationViewPager = findViewById(R.id.AHBottomNavigationViewPager);
        adapter=new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        ahBottomNavigationViewPager.setAdapter(adapter);
        ahBottomNavigationViewPager.setPagingEnabled(true);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.home_icon, R.color.color_tab_1);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.card_icon, R.color.color_tab_2);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.user_icon, R.color.color_tab_3);

// Add items
        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);
        ahBottomNavigation.addItem(item3);
        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                ahBottomNavigationViewPager.setCurrentItem(position);
                return true;
            }
        });
        ahBottomNavigationViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                ahBottomNavigation.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    public View getViewEndAnimation() {
        return viewEndAnimation;
    }

    public ImageView getViewAnimation() {
        return viewAnimation;
    }
    public void  setCountProductCart(int count){
        mCountProduct = count;
        if(count==0){
            ahBottomNavigation.setNotification("", 1);
        }else {
            AHNotification notification = new AHNotification.Builder()
                    .setText(String.valueOf(count))
                    .setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red))
                    .setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white))
                    .build();
            ahBottomNavigation.setNotification(notification, 1);
        }

    }

    public int getmCountProduct() {
        return mCountProduct;
    }

    public AHBottomNavigationViewPager getAhBottomNavigationViewPager() {
        return ahBottomNavigationViewPager;
    }

    public void setAhBottomNavigationViewPager(AHBottomNavigationViewPager ahBottomNavigationViewPager) {
        this.ahBottomNavigationViewPager = ahBottomNavigationViewPager;
    }
    public void getUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user ==null){
            setG_uid(null);
        }else {
                // UID specific to the provider
                String uid = user.getUid();
                setG_uid(uid);
                SetPhoneNumber(uid);

        }
    }
    public void SetPhoneNumber(String uid){
        Intent i = getIntent();
        phoneNumber = i.getStringExtra("phone_number");
        System.out.println(phoneNumber);
        if(phoneNumber!=null){
            myRefProfile.child(uid).child("phoneNumber").setValue(phoneNumber);
        }else {
            return;
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //// Chưa sử lý out sate
    }

}