package com.example.hp.timeapp.auth;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import com.example.hp.timeapp.R;
import com.example.hp.timeapp.settings.PrefixEditText;
import com.example.hp.timeapp.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import static com.example.hp.timeapp.util.Constants.USER_DATA;

public class PhoneLoginActivity extends AppCompatActivity {

    private static final String TAG = "PhoneActivity";

    // UI references.
    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    private FirebaseAuth fbAuth;

    private ProgressDialog dialog;
    private PrefixEditText phoneText;
    private EditText codeText;
    private Button verifyButton;
//    private Button sendButton;
    private Button resendButton;
//    private Button signoutButton;
    private TextView statusText;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);


        // Set up the login form.
//        phoneText = (PrefixEditText) findViewById(R.id.phoneText);
        codeText = (EditText) findViewById(R.id.codeText);
        verifyButton = (Button) findViewById(R.id.verifyButton);
//        sendButton = (Button) findViewById(R.id.sendButton);
        resendButton = (Button) findViewById(R.id.resendButton);
//        signoutButton = (Button) findViewById(R.id.signoutButton);
        statusText = (TextView) findViewById(R.id.statusText);



        verifyButton.setEnabled(false);
        //77752416951
        resendButton.setEnabled(false);
//        signoutButton.setEnabled(false);


        fbAuth = FirebaseAuth.getInstance();
        sendCode();
        countDown();

        FirebaseUser firebaseUser = fbAuth.getCurrentUser();
        sharedPreferences  = getSharedPreferences(USER_DATA, Context.MODE_PRIVATE);

//        fbAuth.
    }


    public void countDown(){

       new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                statusText.setText("Вы получите код в течении 0:" + millisUntilFinished / 1000 + "сек.");
            }

            public void onFinish() {
                statusText.setText("Если не получили код, повторите еще раз!");
            }
        }.start();
    }

    public void sendCode() {

        String phone = getIntent().getStringExtra("PHONE_NUMBER");
        String phoneNumber = phone;

        Log.d(TAG,"Number is " + phoneNumber);

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationCallbacks);
    }


    private void setUpVerificatonCallbacks() {

        verificationCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(
                            PhoneAuthCredential credential) {

//                        signoutButton.setEnabled(true);


                        dialog = ProgressDialog.show(PhoneLoginActivity.this, "Авторизация",
                                "Загрузка...", true);


                        resendButton.setEnabled(false);
                        verifyButton.setEnabled(false);
                        codeText.setText("");
                        signInWithPhoneAuthCredential(credential);

                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Log.d(TAG, "Invalid credential: "
                                    + e.getLocalizedMessage());
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // SMS quota exceeded
                            Log.d(TAG, "SMS Quota exceeded.");
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {

                        phoneVerificationId = verificationId;
                        resendToken = token;

                        verifyButton.setEnabled(true);
//                        sendButton.setEnabled(false);
                        resendButton.setEnabled(true);
                    }
                };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            signoutButton.setEnabled(true);
                            codeText.setText("");
//                            statusText.setText("Signed In");
                            resendButton.setEnabled(false);
                            verifyButton.setEnabled(false);


                            // getting user from a fire base server
                            FirebaseUser user = task.getResult().getUser();

                            Log.d(TAG,"FIRE BASE NAme " + user);
                            Log.d(TAG,"FIRE BASE NAme " + user.getUid());
                            Log.d(TAG,"FIRE BASE Number " + user.getPhoneNumber());
                            Log.d(TAG,"FIRE BASE Token " + user.getIdToken(true));
                            Log.d(TAG,"FIRE BASE Provide " + user.getProviderId());


                            saveData(user.getUid(),user.getPhoneNumber());

                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid

                            }
                        }
                    }
                });
    }

    void saveData(String user_uid,String phoneNumber) {
        SharedPreferences.Editor sPref = sharedPreferences.edit();

        sPref.putString("user_uid", user_uid);
        sPref.putString("user_phone_number",phoneNumber);
        sPref.commit();

        Toast.makeText(this, "USer data saved", Toast.LENGTH_SHORT).show();
    }



    public void resendCode(View view) {

        String phoneNumber = phoneText.getText().toString();

//        countDown();//again calling count down function for 60 sec

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // destroy dialog loading bar
//        dialog.cancel();
        //destroy counter
//        countDownTimer.cancel();
        dialog.dismiss();
    }

    public void verifyCode(View view) {

        String code = codeText.getText().toString();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationId, code);

        signInWithPhoneAuthCredential(credential);
    }
}

