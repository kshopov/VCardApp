package com.example.qrandroidapp;

import http.HttpClient;
import model.User;

import org.json.JSONException;
import org.json.JSONObject;

import utils.Validator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterPersonalActivity extends Activity {
	
	private EditText firstName = null;
	private EditText lastName = null;
	private EditText position = null;
	private EditText businessPhone = null;
	private EditText personalPhone = null;
	private EditText userEmail = null;
	private EditText skype = null;
	private EditText facebook = null;
	private ProgressDialog dl = null;
	
	private User user = null;
	
	private String username = null;
	private String password = null;
	private String email = null;
	
	private static final String URL = "http://parite.bg/mobile_registration_user.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_personal);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			this.username = extras.getString("username");
			this.password = extras.getString("password");
			this.email = extras.getString("email");
		}
		
		firstName = (EditText) findViewById(R.id.first_name);
		lastName = (EditText) findViewById(R.id.last_name);
		position = (EditText) findViewById(R.id.position);
		businessPhone = (EditText) findViewById(R.id.business_phone);
		personalPhone = (EditText) findViewById(R.id.personal_phone);
		userEmail = (EditText) findViewById(R.id.email);
		skype = (EditText) findViewById(R.id.skype);
		facebook = (EditText) findViewById(R.id.facebook);
		
		user = new User();
	}
	
	public void registerUserProfile(View v) {
		if (collectData()) {
			dl = ProgressDialog.show(this, "Sendig data", "");
			SendData sendData = new SendData();
			sendData.execute(URL);
		}
	}
	
	private boolean collectData() {
		if(!Validator.isValidEmail(userEmail)) {
			userEmail.setError(getString(R.string.not_valid_email));
			userEmail.requestFocus();
			return false;
		}
		
		user.setFirstName(firstName.getText().toString());
		user.setLastName(lastName.getText().toString());
		user.setPostion(position.getText().toString());
		user.setBusinessPhone(businessPhone.getText().toString());
		user.setPersonalPhone(personalPhone.getText().toString());
		user.setEmail(userEmail.getText().toString());
		user.setSkype(skype.getText().toString());
		user.setFacebook(facebook.getText().toString());
		
		return true;
	}
	
	private class SendData extends AsyncTask<String, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... params) {
			JSONObject jsonObject = new JSONObject();
			JSONObject results = null;
			String url = params[0];
			try {
				jsonObject.put("username", username);
				jsonObject.put("email", email);
				jsonObject.put("password", password);
				jsonObject.put("firstName", user.getFirstName());
				jsonObject.put("lastName", user.getLastName());
				jsonObject.put("position", user.getPostion());
				jsonObject.put("businessPhone", user.getBusinessPhone());
				jsonObject.put("personalPhone", user.getPersonalPhone());
				jsonObject.put("userEmail", user.getEmail());
				jsonObject.put("skype", user.getSkype());
				jsonObject.put("facebook", user.getFacebook());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			results = HttpClient.SendHttpPost(url, jsonObject);
			
			return results;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			if (result == null) {
				Toast.makeText(RegisterPersonalActivity.this, getText(R.string.server_error), Toast.LENGTH_SHORT).show();
			} else {
				try {
					switch (result.getInt("serverCode")) {
						case 1:
							break;
						case 2:
							AlertDialog.Builder alert = new AlertDialog.Builder(RegisterPersonalActivity.this);
							alert.setTitle("Successful registration");
							alert.setMessage("Your Registration was successful");
							alert.setPositiveButton("Login",
									new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											Intent loginIntent = new Intent(RegisterPersonalActivity.this, LoginActivity.class);
											startActivity(loginIntent);
										}
									});
							alert.setNeutralButton("Main", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											Intent loginIntent = new Intent(RegisterPersonalActivity.this, MainActivity.class);
											startActivity(loginIntent);
										}
									});
							alert.show();
						break;
						default:
							//default
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			dl.hide();
		}
		
	}
}
