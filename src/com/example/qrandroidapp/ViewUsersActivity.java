package com.example.qrandroidapp;

import java.util.List;

import model.User;
import adapters.UsersListAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ViewUsersActivity extends Activity {
	
	private QRApp app = null;
	
	private List<User> users = null;
	private ListView list = null;
	private UsersListAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_activity);
		app = (QRApp) getApplication();
		list = (ListView) findViewById(R.id.list);
		
		users = app.getDataManager().getAllUsers();
		adapter = new UsersListAdapter(this, users);
		list.setAdapter(adapter);
	}
}
