package com.example.addtocard1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SigninActivity extends AppCompatActivity {
    private LinearLayout layoutSignup;
    EditText edtEmail , edtPassword;
    Button btnLogin;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        getSupportActionBar().hide();
        initUI();
        initListener();
    }
    private void  initUI(){
        progressDialog = new ProgressDialog(this);
        layoutSignup = findViewById(R.id.layout_sign_up);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btnLogin);

    }
    private void initListener(){
        layoutSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SigninActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnclickLogin();
            }
        });
    }

    private void OnclickLogin() {
        String Email = edtEmail.getText().toString().trim();
        String PassWord = edtPassword.getText().toString().toString();
        if (Email.isEmpty() || PassWord.isEmpty()){
            Toast.makeText(SigninActivity.this,"Tài khoản hoặc mật khẩu không được trống", Toast.LENGTH_LONG).show();
            return;
        }
        /////// Check Email và password nhé
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(Email, PassWord)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Intent intent =new Intent(SigninActivity.this,MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            progressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SigninActivity.this,"Tài khoản hoặc mật khẩu không chính xác", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}