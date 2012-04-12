package edu.ncsu.csc.crowdfarm.activities;

import edu.ncsu.csc.crowdfarm.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashScreenActivity extends Activity {
	
	protected static final String PREF_FILE = "prefs";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		SharedPreferences prefs = getSharedPreferences(PREF_FILE, MODE_PRIVATE);
		String loggedIn = prefs.getString("loggedInEmail", null);
		
		if(loggedIn == null) {
			Intent loginIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
			SplashScreenActivity.this.startActivity(loginIntent);
		} else {
			Intent homeIntent = new Intent(SplashScreenActivity.this, HomeActivity.class);
			SplashScreenActivity.this.startActivity(homeIntent);
		}
	}

}
