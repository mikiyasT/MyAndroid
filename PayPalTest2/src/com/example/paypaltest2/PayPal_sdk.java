package com.example.paypaltest2;

import java.math.BigDecimal;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.paypaltest2.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;


public class PayPal_sdk extends Activity{
	Button button_paywithpaypal;
	EditText editText_friend_name;
	EditText editText_amount;
	private static final int REQUEST_CODE_PAYMENT = 1;
	
	
	
	private static PayPalConfiguration paypalConfig = new PayPalConfiguration()
			.environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
			.clientId(Util.paypal_sdk_id)
			.acceptCreditCards(false);
			
			
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_pay_pal_sdk);
		
		Intent intent = new Intent(this,PayPalService.class);
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalConfig);
		try{
		startService(intent);
		}catch(Exception e){
			e.printStackTrace();
			Log.d("Exception","paypal start service error ");
		}
		
		editText_friend_name= (EditText)findViewById(R.id.editText_friend_name);
		editText_amount= (EditText) findViewById(R.id.editText_amount);
		button_paywithpaypal= (Button)findViewById(R.id.button_paywithpaypal);
		
		button_paywithpaypal.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				launchPayPalPayment();
				
			}			
		});
		
			
	}
	private void launchPayPalPayment() {
		PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(editText_amount.getText().toString()),"USD",
				editText_friend_name.getText().toString(),
				PayPalPayment.PAYMENT_INTENT_SALE);
				Intent intent = new Intent(PayPal_sdk.this, PaymentActivity.class);
				intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,paypalConfig);
				intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
				startActivityForResult(intent, REQUEST_CODE_PAYMENT);
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode == REQUEST_CODE_PAYMENT) 
		{
			if (resultCode == Activity.RESULT_OK) {
				PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
				 try {
					Log.d("Confirmation", confirm.toJSONObject().toString(4));
					 Log.d("COnfirmation", confirm.getPayment().toJSONObject().toString(4));
				} catch (JSONException e) {
					Log.d("Confirmation","Exception occered while confirming payment");
					e.printStackTrace();
				}
                
				Toast.makeText(getApplicationContext(), "Payment done succesfully ",Toast.LENGTH_LONG).show();
			}
			else if (resultCode == Activity.RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(), "Payment Canceled , Try again ",Toast.LENGTH_LONG).show();
			}
			else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
				Toast.makeText(getApplicationContext(), "Payment failed , Try again ",Toast.LENGTH_LONG).show();
			}
		}
	}
	
	

}
