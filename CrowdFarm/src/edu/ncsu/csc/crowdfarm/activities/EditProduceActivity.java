package edu.ncsu.csc.crowdfarm.activities;

import java.util.Scanner;

import edu.ncsu.csc.crowdfarm.R;
import edu.ncsu.csc.crowdfarm.beans.ProduceBean;
import edu.ncsu.csc.crowdfarm.rest.CrowdFarmRest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditProduceActivity extends Activity {
	
	private EditText produceType;
	private EditText amount;
	private EditText pickDate;
	private EditText shelfLife;
	private EditText cost;
	private Spinner amountSpinner;
	private Spinner shelfLifeSpinner;
	private ProduceBean currentBean;
	private String itemType;
	private String itemDate;
	private String uid;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editproduce);
        
        //Get the current user id
        
        //Get references to the EditText objects in the UI
        produceType = (EditText) findViewById(R.id.produceInput);
        amount = (EditText) findViewById(R.id.amountInput);
        pickDate = (EditText) findViewById(R.id.dateInput);
        shelfLife = (EditText) findViewById(R.id.shelfInput);
        cost = (EditText) findViewById(R.id.costInput);
        
        ArrayAdapter<CharSequence> quantityAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.quantityTypes, android.R.layout.simple_spinner_item );
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amountSpinner = (Spinner)findViewById(R.id.quantitySpinner);
        amountSpinner.setAdapter(quantityAdapter);
        
        ArrayAdapter<CharSequence> shelfAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.shelfLifeTypes, android.R.layout.simple_spinner_item );
        shelfAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shelfLifeSpinner = (Spinner)findViewById(R.id.shelfSpinner);
        shelfLifeSpinner.setAdapter(shelfAdapter);
        
        Bundle extra = getIntent().getExtras();
        String itemString = extra.getString("item");
        
        Scanner s = new Scanner(itemString);
        itemType = "";
        itemDate = "";
        if(s.hasNext()) {
        	itemType = s.next();
        }
        if(s.hasNext()) {
        	s.next();
        }
        if(s.hasNext()) {
        	itemDate = s.next();
        }
        
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
		uid = prefs.getString("loggedInUid", null);
        
        CrowdFarmRest cfr = new CrowdFarmRest();
        try {
			currentBean = cfr.getProduceItem(getApplicationContext(), uid, itemType, itemDate);
		} catch (Exception e) {
			}
        
        produceType.setText(currentBean.getType());
        amount.setText(currentBean.getAmount());
        pickDate.setText(currentBean.getPickDate());
        shelfLife.setText(currentBean.getShelfLife());
        cost.setText(currentBean.getCost());
        
        for(int i = 0; i < amountSpinner.getAdapter().getCount(); i++) {
        	if( ((String)amountSpinner.getItemAtPosition(i)).equals(currentBean.getMeasure()) ) {
        		amountSpinner.setSelection(i);
        	}
        }
        
        for(int i = 0; i < shelfLifeSpinner.getAdapter().getCount(); i++) {
        	if( ((String)shelfLifeSpinner.getItemAtPosition(i)).equals(currentBean.getLifeUnit()) ) {
        		shelfLifeSpinner.setSelection(i);
        	}
        }
        
        
        
	}
	
	public void delete(View v) {
		CrowdFarmRest cfr = new CrowdFarmRest();
		try {
			cfr.deleteProduce(getApplicationContext(), uid, itemType, itemDate);
		} catch (Exception e) {
			String errorMessage = "An error has occurred.  Ensure you have a network connections and try again.";
			Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
		}
		
		Intent i = new Intent(EditProduceActivity.this, ViewProduceActivity.class);
		EditProduceActivity.this.startActivity(i);
	}
	
	public void submit(View v) {
		CrowdFarmRest cfr = new CrowdFarmRest();
		ProduceBean produceBean = new ProduceBean();
		
		produceBean.setType(produceType.getText().toString());
		produceBean.setAmount(amount.getText().toString());
		produceBean.setMeasure(amountSpinner.getSelectedItem().toString());
		produceBean.setCost(cost.getText().toString());
		produceBean.setPickDate(pickDate.getText().toString());
		produceBean.setShelfLife(shelfLife.getText().toString());
		produceBean.setLifeUnit(shelfLifeSpinner.getSelectedItem().toString());
		
		try {
			cfr.editProduce(getApplicationContext(), uid, itemType, itemDate, produceBean);
		} catch (Exception e) {
			String errorMessage = "An error has occurred.  Ensure you have a network connections and try again.";
			Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
		}
		
		Intent i = new Intent(EditProduceActivity.this, ViewProduceActivity.class);
		EditProduceActivity.this.startActivity(i);
	}

}
