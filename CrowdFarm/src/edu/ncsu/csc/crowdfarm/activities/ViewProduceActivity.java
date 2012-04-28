package edu.ncsu.csc.crowdfarm.activities;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc.crowdfarm.R;
import edu.ncsu.csc.crowdfarm.beans.ProduceBean;
import edu.ncsu.csc.crowdfarm.rest.CrowdFarmRest;
import android.app.Activity;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ViewProduceActivity extends ListActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		CrowdFarmRest cfr = new CrowdFarmRest();
		SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
		String uid = prefs.getString("loggedInUid", null);
		List<String> produceStringList = null;
		Log.v("MyActivity", "instantiate variables");
		
		try {
			produceStringList = formatProduceString(cfr.loadProduce(getApplicationContext(), uid));
			Log.v("MyActivity", "create list");
			Log.v("MyActivity", cfr.loadProduce(getApplicationContext(), uid).size() + "");
		} catch(Exception e) {
			String errorMessage = "The produce file could not be found.";
			Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
			Log.v("MyActivity", "problem making list");
		}
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.produceitem, produceStringList));
		Log.v("MyActivity", "list set");
		ListView lv = getListView();
	}
	
	private List<String> formatProduceString(List<ProduceBean> produceList) {
		List<String> returnList = new ArrayList<String>();
		
		for(ProduceBean bean: produceList) {
			returnList.add(bean.getType() + " : " + bean.getPickDate());
			Log.v("MyActivity", bean.getType() + " : " + bean.getPickDate());
		}
		
		return returnList;
	}

}
