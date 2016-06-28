package com.example.paypaltest2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.paypaltest2.R;

public class MainActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_pal_selection);
		
		Button button_paypal_sdk = (Button)findViewById(R.id.button_paypal_sdk);
		button_paypal_sdk.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				startActivity(new Intent(MainActivity.this,PayPal_sdk.class));
				
			}
		});
		Button button_paypal_mpl = (Button)findViewById(R.id.button_paypal_mpl);
		button_paypal_mpl.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// //startActivity(new Intent(PayPal_selection.this,PayPal_mpl.class));
				
			}
		});
	}
	
	
	


}
