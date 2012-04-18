package edu.ncsu.csc.crowdfarm.activities;

import edu.ncsu.csc.crowdfarm.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
	}

	public void addProduce(View v) {
		Intent addIntent = new Intent(HomeActivity.this, AddProduceActivity.class);
		HomeActivity.this.startActivity(addIntent);
	}
	
	public void viewProduce(View v) {
		Intent viewIntent = new Intent(HomeActivity.this, ViewProduceActivity.class);
		HomeActivity.this.startActivity(viewIntent);
	}
	
	public void viewSettings(View v) {
		Intent settingsIntent = new Intent(HomeActivity.this, SettingsActivity.class);
		HomeActivity.this.startActivity(settingsIntent);
	}
}
