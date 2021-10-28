package com.example.addtocard1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumberActivity extends AppCompatActivity {
    private static  final String TAG= VerifyPhoneNumberActivity.class.getName();
    private EditText edtPhoneNumber,edtOtp;
    private Button btnEnterPhone,xacnhanma;
    private FirebaseAuth mAuth;
    LinearLayout layoutenterotp;
    TextView sendOTPagain;
    String mverificationID;

    private PhoneAuthProvider.ForceResendingToken mforceResendingToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyphone_number);
        initUi();
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
//        mAuth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);
        btnEnterPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phonenumber = "+84"+edtPhoneNumber.getText().toString().trim();
                String strPhoneNumber = phonenumber;

                onclickVerifyPhoneNumber(strPhoneNumber);
            }
        });
        sendOTPagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phonenumber = "+84"+edtPhoneNumber.getText().toString().trim();
                String strPhoneNumber = phonenumber;
                Toast.makeText(VerifyPhoneNumberActivity.this,"Đang gửi lại mã",Toast.LENGTH_LONG).show();
                sendotpagain(strPhoneNumber);
            }
        });
    }

    private void sendotpagain(String phonenumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phonenumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setForceResendingToken(mforceResendingToken)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerifyPhoneNumberActivity.this,"Verify fail",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationID, forceResendingToken);
                                mverificationID = verificationID;
                                mforceResendingToken = forceResendingToken;

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void initUi(){
        edtPhoneNumber = findViewById(R.id.edit_phone_number);
        btnEnterPhone = findViewById(R.id.btn_enter_phone_number);
        layoutenterotp = findViewById(R.id.layout_enterotp);
        xacnhanma = findViewById(R.id.btn_xacnhanma);
        edtOtp = findViewById(R.id.edt_otp);
        sendOTPagain = findViewById(R.id.tv_sendagain);
        TextView tvloginemail = findViewById(R.id.tvlogin_email);
        tvloginemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void onclickVerifyPhoneNumber(String strPhoneNumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(strPhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerifyPhoneNumberActivity.this,"Verify fail",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationID, forceResendingToken);
//                                goToEnterOTP(strPhoneNumber,verificationID);

                                 viewenterOtp(strPhoneNumber,verificationID);

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            gotoMainActivity(user.getPhoneNumber());
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(VerifyPhoneNumberActivity.this,"Đăng nhập thất bại",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void gotoMainActivity(String phoneNumber) {
        Intent intent = new Intent(VerifyPhoneNumberActivity.this,MainActivity.class);
        intent.putExtra("phone_number",phoneNumber);
        startActivity(intent);

    }
//    private void goToEnterOTP(String strPhoneNumber, String verificationID) {
//        Intent intent = new Intent(VerifyPhoneNumberActivity.this,EnterOTPActivity.class);
//        intent.putExtra("phone_number",strPhoneNumber);
//        intent.putExtra("verification_ID",verificationID);
//        startActivity(intent);
//    }
    private void viewenterOtp(String strPhoneNumber,String verificationID){
        mverificationID=verificationID;
        layoutenterotp.setVisibility(View.VISIBLE);
        xacnhanma.setVisibility(View.VISIBLE);
        btnEnterPhone.setVisibility(View.GONE);
        xacnhanma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String OTP = edtOtp.getText().toString().trim();
                onclickSendOTP(verificationID,OTP);
            }
        });
    }

    private void onclickSendOTP(String verificationID, String otp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, otp);
        signInWithPhoneAuthCredential(credential);
    }


}