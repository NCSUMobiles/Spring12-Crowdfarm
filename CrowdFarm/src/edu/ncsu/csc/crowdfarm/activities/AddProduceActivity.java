package edu.ncsu.csc.crowdfarm.activities;

import edu.ncsu.csc.crowdfarm.R;
import edu.ncsu.csc.crowdfarm.beans.ProduceBean;
import edu.ncsu.csc.crowdfarm.rest.CrowdFarmRest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class AddProduceActivity extends Activity {
	
	private EditText produceType;
	private EditText amount;
	private EditText pickDate;
	private EditText shelfLife;
	private EditText cost;
	private Spinner amountSpinner;
	private Spinner shelfLifeSpinner;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addproduce);
        
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
    }
	
	public void clear(View v) {
		produceType.setText("");
		amount.setText("");
		pickDate.setText("");
		shelfLife.setText("");
		cost.setText("");
		
	}

	public void submit (View v) {
		ProduceBean produceBean = new ProduceBean();
		
		produceBean.setType(produceType.getText().toString());
		produceBean.setAmount(amount.getText().toString());
		produceBean.setMeasure(amountSpinner.getSelectedItem().toString());
		produceBean.setCost(cost.getText().toString());
		produceBean.setPickDate(pickDate.getText().toString());
		produceBean.setShelfLife(shelfLife.getText().toString());
		produceBean.setLifeUnit(shelfLifeSpinner.getSelectedItem().toString());
		
		try {
			//produceBean.validate();
			CrowdFarmRest cfr = new CrowdFarmRest();
			cfr.addNewProduce(produceBean);
		} catch(Exception e) {
			//toast an error message
		}
		
		Intent homeIntent = new Intent(AddProduceActivity.this, HomeActivity.class);
		AddProduceActivity.this.startActivity(homeIntent);
	}
}
