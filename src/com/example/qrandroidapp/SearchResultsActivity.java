package com.example.qrandroidapp;

import java.util.ArrayList;
import java.util.List;

import model.Organisation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import adapters.OrganisationListAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class SearchResultsActivity extends Activity {
	
	private List<Organisation> organisations = null;
	
	private ListView list = null;
	private OrganisationListAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_activity);
		organisations = new ArrayList<Organisation>();
		list = (ListView) findViewById(R.id.list);
		try {
			JSONArray jsonArray = new JSONArray(getIntent().getStringExtra("results"));
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonobj = jsonArray.getJSONObject(i);
				Organisation organisation = new Organisation();
				organisation.setId(jsonobj.getInt("account_id"));
				organisation.setName(jsonobj.getString("company_name"));
				organisation.setEmail(jsonobj.getString("company_email"));
				organisation.setAddress(jsonobj.getString("company_address"));
				organisation.setWebPage(jsonobj.getString("company_web_page"));
				organisation.setPhone(jsonobj.getString("company_phone"));
				organisation.setMobile(jsonobj.getString("company_mobile"));
				organisation.setFax(jsonobj.getString("company_fax"));
				organisation.setCountry_id(jsonobj.getInt("country_id"));
				organisation.setCity(jsonobj.getString("city"));
				organisation.setBranch_id(jsonobj.getInt("branch_id"));
				organisation.setGpsLongtitude(jsonobj.getDouble("gps_longtitude"));
				organisation.setGpsLatitude(jsonobj.getDouble("gps_latitude"));
				
				organisations.add(organisation);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		adapter = new OrganisationListAdapter(this, organisations);
		list.setAdapter(adapter);
	}
}
