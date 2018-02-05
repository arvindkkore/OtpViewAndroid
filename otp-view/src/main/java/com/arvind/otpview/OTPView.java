package com.arvind.otpview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.graphics.TypefaceCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class OTPView extends FrameLayout
{
  OnCompleteListener  onCompleteListener;
  List<TextView> textViews=new ArrayList<>();
  private EditText otp_view;
  private Context context;
  private int length;
  private int color;
  private  int text_color;
  private boolean show_secure=false;
  private String secure_symbol="*";
  private boolean zero_allowed_begining=false;
  private int border_color;
  private int inner_color;
  private boolean show_placeholder=false;
  private String placeholder="*";
  int BG_TYPE;
  int min_length=3;
  int max_length=6;
  TypefaceCompat typefaceCompat;
  LinearLayout linearLayout_root;

  public OTPView(Context context) {
    super(context);
    this.context=context;
    init(null);
  }

  public OTPView(Context context, AttributeSet attrs) {
      super(context, attrs);
      this.context=context;
      init(attrs);
  }

  public OTPView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
      this.context=context;
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    TypedArray styles = getContext().obtainStyledAttributes(attrs, R.styleable.OTPView);
    LayoutInflater mInflater =
        (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.otpview_layout, this);

      show_secure=styles.getBoolean(R.styleable.OTPView_is_secure,false);
      zero_allowed_begining=styles.getBoolean(R.styleable.OTPView_zero_allowed_begining,false);
      secure_symbol=styles.getString(R.styleable.OTPView_secure_symbol);
      length=styles.getInt(R.styleable.OTPView_length,min_length);
      if (length>max_length)
      {
         length=max_length;
      }
      border_color = styles.getColor(R.styleable.OTPView_border_color, 0xff000000);
      inner_color = styles.getColor(R.styleable.OTPView_inner_color, 0xff000000);
      text_color = styles.getColor(R.styleable.OTPView_text_color, 0xff000000);
      BG_TYPE=styles.getInteger(R.styleable.OTPView_BG_TYPE,0);
      Log.e("TAG", "init: "+BG_TYPE);
      linearLayout_root=(LinearLayout) findViewById(R.id.root_view);
    otp_view=(EditText) findViewById(R.id.edit_otp_view);
    otp_view.setFilters(new InputFilter[] {new InputFilter.LengthFilter(length)});
    setTextView();
    styleEditTexts(styles);
    styles.recycle();
  }
  
  /**
     * Get an instance of the present otp
     */
    private String makeOTP(){



        return otp_view.getText().toString();
    }

    /**
     * Checks if all four fields have been filled
     * @return length of OTP
     */
    public boolean hasValidOTP()
    {
        return makeOTP().length()==length;
    }

    /**
     * Returns the present otp entered by the user
     * @return OTP
     */
    public String getOTP(){
        return makeOTP();
    }

    /**
     * Used to set the OTP. More of cosmetic value than functional value
     * @param otp Send the four digit otp
     */
    public void setOTP(String otp){
        if(otp.length()!=length){
            Log.e("OTPView","Invalid otp param");
            return;
        }

    }

  private void styleEditTexts(TypedArray styles)
  {

    setEditTextInputStyle(styles);

  }

  private void setEditTextInputStyle(TypedArray styles) {
    /*int inputType =
        styles.getInt(R.styleable.OtpView_android_inputType, EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
    mOtpOneField.setInputType(inputType);
    mOtpTwoField.setInputType(inputType);
    mOtpThreeField.setInputType(inputType);
    mOtpFourField.setInputType(inputType);
    String text = styles.getString(R.styleable.OtpView_otp);
    if (!TextUtils.isEmpty(text) && text.length() == 4) {
      mOtpOneField.setText(String.valueOf(text.charAt(0)));
      mOtpTwoField.setText(String.valueOf(text.charAt(1)));
      mOtpThreeField.setText(String.valueOf(text.charAt(2)));
      mOtpFourField.setText(String.valueOf(text.charAt(3)));
    }*/
  //  setFocusListener();
    setOnTextChangeListener();
  }




  public void disableKeypad() {
    OnTouchListener touchListener = new OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        v.onTouchEvent(event);
        InputMethodManager imm =
            (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
          imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return true;
      }
    };



  }

  public void enableKeypad() {
    OnTouchListener touchListener = new OnTouchListener()
    {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        return false;
      }
    };

  }

  /*public EditText getCurrentFoucusedEditText() {
    return mCurrentlyFocusedEditText;
  }*/

  public void setListener(OnCompleteListener listener)
  {
   this.onCompleteListener=listener;
  }
  private void setOnTextChangeListener() {
      TextWatcher textWatcher = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

         /* if (otp_view.getText().toString().matches("^0") && !zero_allowed_begining)
          {
              otp_view.setText("");
          }*/
      }

      @Override
      public void afterTextChanged(Editable s) {

          Log.e("TAG", "afterTextChanged: "+s+" "+s.length());

          for (int i=0;i<length;i++)
          {
           textViews.get(i).setText("");
          }
          for (int i=0;i<s.length();i++)
          {
              textViews.get(i).setText(""+(show_secure?secure_symbol:s.charAt(i)));
          }
          if (s.length()==length)
          {
              try {
                  InputMethodManager imm =
                          (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                  if (imm != null) {
                      imm.hideSoftInputFromWindow(getWindowToken(), 0);
                  }
                  if (onCompleteListener!=null)
                  {
                      //Toast.makeText(context, otp_view.getText().toString(), Toast.LENGTH_SHORT).show();
                      onCompleteListener.onOTPComplete(otp_view.getText().toString());
                  }
              }catch (Exception e)
              {
                  e.printStackTrace();
              }
          }


      }
    };
    otp_view.addTextChangedListener(textWatcher);

  }

  OnClickListener onClickListener=new OnClickListener() {
      @Override
      public void onClick(View view) {
          Log.e("TAG", "onClick: " );
          otp_view.setFocusableInTouchMode(true);
          otp_view.setFocusable(true);
          otp_view.requestFocus();
          InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
          imm.showSoftInput(otp_view, InputMethodManager.SHOW_IMPLICIT);
      }
  } ;

  /*public void simulateDeletePress() {
    mCurrentlyFocusedEditText.setText("");
  }*/

   OnKeyListener onKeyListener=new OnKeyListener() {
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
      //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
      if(keyCode == KeyEvent.KEYCODE_DEL) {
        //this is for backspace
        Log.e("TAG", "onKey: KEYCODE_DEL");
      }
      return false;
    }
  };



    public void setTextView()
    {
       for (int i=0;i<length;i++)
       {
         LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(100, 100);
         params.setMargins(0,0,16,0);
         TextView textView=new TextView(getContext());
         textView.setTextColor(text_color);
         textView.setGravity(Gravity.CENTER );
         textView.setTextSize(20);
         GradientDrawable drawable;
         switch (BG_TYPE)
         {
             case 0:
              break;
              case 1:
                 textView.setBackgroundResource(R.drawable.sample_background);
                     drawable = (GradientDrawable) textView.getBackground();
                     drawable.setColor(inner_color);
                     drawable.setStroke(2,border_color);
                 break;
             case 2:

                 textView.setBackgroundResource(R.drawable.test);
                 LayerDrawable layerDrawable = (LayerDrawable)textView.getBackground();
                 GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable
                         .findDrawableByLayerId(R.id.first_layer);
                 gradientDrawable.setStroke(3,border_color);

                 break;
         }
       //  textView.setBackground(getResources().getDrawable(R.drawable.sample_background));
         textView.setId(i);
         textView.setTag("text_"+i);
         textView.setLayoutParams(params);
         linearLayout_root.addView(textView);
         textView.setOnClickListener(onClickListener);
         textViews.add(textView);
       }
    }
    public static int getPixelValue(Context context, int dimenId) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dimenId,
                resources.getDisplayMetrics()
        );
    }



}
