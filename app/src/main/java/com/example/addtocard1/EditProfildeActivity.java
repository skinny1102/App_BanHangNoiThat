package com.example.addtocard1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.addtocard1.Doituong.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfildeActivity extends AppCompatActivity {
    private String USER_ID;
    EditText edtFullName,edtEmail,edtAddress,edtphoneNumber;
    Button btnUpdate;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refProfile = database.getReference("profile");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profilde);
        getSupportActionBar().hide();
        Intent i = getIntent();
        USER_ID=i.getStringExtra("USER_ID");
        initUi();
        Imgback();
        InitDataUIProfile();
        clickupdateProfile();
    }
    private void Imgback(){
        ImageView imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void initUi(){
        edtFullName = findViewById(R.id.edt_username);
        edtEmail = findViewById(R.id.edt_email);
        edtAddress=findViewById(R.id.edt_address);
        edtphoneNumber = findViewById(R.id.edit_phone_number);
        btnUpdate=findViewById(R.id.btn_update);
    }
    private void InitDataUIProfile(){
        refProfile.child(USER_ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userObj = snapshot.getValue(User.class);
                if(userObj!=null){
                    if (userObj.getFullName()!=null){
                        edtFullName.setText(userObj.getFullName());
                    }
                    if (userObj.getEmail()!=null){
                        edtEmail.setText(userObj.getEmail());
                    }
                    if (userObj.getPhoneNumber()!=null){
                        edtphoneNumber.setText(userObj.getPhoneNumber());
                    }
                    if(userObj.getAddress()!=null){
                        edtAddress.setText(userObj.getAddress());
                    }
                }else {
                    return;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private  void clickupdateProfile(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });
    }

    private void updateProfile() {
        String fullName = edtFullName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String phoneNumber = edtphoneNumber.getText().toString().trim();
        User user = new User("none",fullName,phoneNumber,email,address);
        if(fullName.isEmpty() || email.isEmpty()||address.isEmpty()
        || phoneNumber.isEmpty()){
            Toast.makeText(EditProfildeActivity.this,"Hãy điền đầy đủ các trường thông tin ",Toast.LENGTH_LONG).show();
        }else {
            refProfile.child(USER_ID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(EditProfildeActivity.this,"Cập nhật thành công",Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }

    }
}