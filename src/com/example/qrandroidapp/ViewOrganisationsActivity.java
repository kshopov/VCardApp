package com.example.qrandroidapp;

import java.util.List;

import model.Organisation;
import adapters.OrganisationListAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ViewOrganisationsActivity extends Activity {
	
	private ListView list = null;
	private OrganisationListAdapter adapter = null;
	public ViewOrganisationsActivity activity = null;
	public List<Organisation> organisations = null;
	private QRApp app = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_activity);
		app = (QRApp) getApplication();
		
		organisations = app.getDataManager().getAllOrganisations();
		
		activity = this;
		list = (ListView) findViewById(R.id.list);
		
		adapter = new OrganisationListAdapter(this, organisations);
		list.setAdapter(adapter);
	}
	
}