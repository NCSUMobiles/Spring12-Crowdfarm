package edu.ncsu.csc.crowdfarm.activities;

import edu.ncsu.csc.crowdfarm.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;

public class RegisterActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
	}
	
	public void next(View view) {
		final ViewFlipper regFlipper = (ViewFlipper)findViewById(R.id.registerFlipper);
		regFlipper.showNext();
	}
	
	public void back(View view) {
		final ViewFlipper regFlipper = (ViewFlipper)findViewById(R.id.registerFlipper);
		regFlipper.showPrevious();
	}
	
	public void backToLogin(View view) {
		Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
		RegisterActivity.this.startActivity(loginIntent);
	}
	
	public void submit(View view) {
		
	}

}
