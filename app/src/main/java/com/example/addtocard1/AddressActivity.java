package com.example.addtocard1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity {
    private String USER_ID;
    EditText edtAddress;
    Button btnaddAddress;
    ListView listView;
    List<String> list;
    private ArrayAdapter<String> adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refAddress = database.getReference("profile");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        getSupportActionBar().hide();
        Intent i = getIntent();
        USER_ID = i.getStringExtra("USER_ID");
        getinitView();
        getAddressView();
    }

    private void getinitView() {
        edtAddress = findViewById(R.id.edt_address);
        btnaddAddress = findViewById(R.id.btn_addAddress);
        btnaddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddressActivity.this,"Đã click",Toast.LENGTH_LONG).show();
                String address = edtAddress.getText().toString().trim();
                if(address.isEmpty()){
                    Toast.makeText(AddressActivity.this,"Địa chỉ không được để trống",Toast.LENGTH_LONG).show();
                }else {
                    refAddress.child(USER_ID).child("address").push().setValue(address).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(AddressActivity.this,"Thêm thành công",Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });

    }
    private void getAddressView(){
        listView =findViewById(R.id.lv_addadress);
        adapter = new ArrayAdapter<String>(AddressActivity.this, android.R.layout.simple_list_item_1, getListAddres());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private List<String> getListAddres() {
        List<String> list = new ArrayList<>();
        refAddress.child(USER_ID).child("address").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    if (postSnapshot.getValue()==null){
                        return;
                    }else {
                        list.add(postSnapshot.getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }

}

//    AlertDialog.Builder b = new AlertDialog.Builder(this);
////Thiết lập tiêu đề
//b.setTitle("Xác nhận");
//        b.setMessage("Bạn có đồng ý thoát chương trình không?");
//// Nút Ok
//        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
//public void onClick(DialogInterface dialog, int id) {
//        finish();
//        }
//        });
////Nút Cancel
//        b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
//public void onClick(DialogInterface dialog, int id) {
//        dialog.cancel();
//        }
//        });
////Tạo dialog
//        AlertDialog al = b.create();
////Hiển thị
//        al.show();