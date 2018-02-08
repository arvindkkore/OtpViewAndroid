package com.arvind.otpviewandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.arvind.otpview.OTPView;
import com.arvind.otpview.OnCompleteListener;

public class MainActivity extends AppCompatActivity implements OnCompleteListener {

    OTPView otpView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        otpView= (OTPView) findViewById(R.id.otp_view);
        otpView.setListener(new OnCompleteListener() {
            @Override
            public void onOTPComplete(String otp) {
                Toast.makeText(MainActivity.this, "otp is "+otp, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onOTPComplete(String otp) {

    }
}
