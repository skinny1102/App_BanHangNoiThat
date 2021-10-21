package com.example.addtocard1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddressActivity extends AppCompatActivity {
    private String USER_ID;
    EditText edtAddress;
    Button btnaddAddress;
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
    }

    private void getinitView() {
        edtAddress = findViewById(R.id.edt_address);
        btnaddAddress = findViewById(R.id.btn_addAddress);
        btnaddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = edtAddress.getText().toString().trim();
                if(address.isEmpty()){
                    Toast.makeText(AddressActivity.this,"Địa chỉ không được để trống",Toast.LENGTH_LONG).show();
                }else {
                    refAddress.child(USER_ID).child("address").setValue(address).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(AddressActivity.this,"Thêm thành công",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }


}