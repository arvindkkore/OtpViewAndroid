   #  OtpView
   #  An Android library for OTP View

   ## Download
   Grab via Gradle:
   ```java
   compile 'com.arvind.otpview:otp-view:0.0.4'
   ```
   Or Maven:
   ```java
   <dependency>
    <groupId>com.arvind.otpview</groupId>
     <artifactId>otp-view</artifactId>
     <version>0.0.4</version>
      <type>pom</type>
    </dependency>
   ```
   ## Usage
   ```xml
   <com.arvind.otpview.OTPView
           android:layout_centerInParent="true"
           android:id="@+id/otp_view"
           app:length="6"
           app:secure_symbol="*"
           app:is_secure="true"
           app:zero_allowed_begining="false"
           app:BG_TYPE="UNDERLINE"
           app:border_color="@color/colorPrimaryDark"
           app:inner_color="@color/colorAccent"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           />
```
  ```java
otpView= (OTPView) findViewById(R.id.otp_view);
        otpView.setListener(new OnCompleteListener() {
            @Override
            public void onOTPComplete(String otp) {
                Toast.makeText(MainActivity.this, "otp is "+otp, Toast.LENGTH_SHORT).show();
            }
        });
```