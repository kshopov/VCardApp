package com.example.qrandroidapp;

import http.HttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.qrandroidapp.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private EditText email = null;
	private EditText password = null;
	private EditText passwordAgain = null;
	private EditText username = null;
	private ProgressDialog dl = null;
	
	private boolean isOrganisation = true;
	
	public static final String PREF_NAME = "qrAppFile";
	
	private static final String URL = "http://parite.bg/mobile_registration.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);

		email = (EditText) findViewById(R.id.email_input);
		password = (EditText) findViewById(R.id.password_input);
		passwordAgain = (EditText) findViewById(R.id.password_again);
		username = (EditText) findViewById(R.id.username);

	}
	
	public void next(View v) {
		if (validate()) {
			dl = ProgressDialog.show(this, getText(R.string.sending_data), "");
			SendData sendData = new SendData();
			sendData.execute(URL);
		}
	}
	
	public void onRadioButtonClicked(View view) {
		boolean checked = ((RadioButton) view).isChecked();
		switch (view.getId()) {
		case R.id.radio_organisation :
			if(checked)
			isOrganisation = true;
			break;
		case R.id.radio_personal :
			if(checked)
			isOrganisation = false;
			break;
		default:
			break;
		}
	}

	private boolean validate() {
		if (!utils.Validator.isValidEmail(email)) {
			email.setError(getText(R.string.not_valid_email));
			email.requestFocus();
			return false;
		} else if (utils.Validator.isEmpty(username)) {
			username.setError(getText(R.string.empty_field));
			username.requestFocus();
		} else if (!utils.Validator.isSamevalue(password, passwordAgain)) {
			password.setError(getText(R.string.password_not_match));
			password.requestFocus();
			return false;
		}
		return true;
	}
	
	private class SendData extends AsyncTask<String, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... params) {
			JSONObject jsonObject = new JSONObject();
			JSONObject results = null;
			String url = params[0];
			try {
				jsonObject.put("username", username.getText().toString());
				jsonObject.put("email", email.getText().toString());
				jsonObject.put("password", password.getText().toString());
				jsonObject.put("passwordAgain", passwordAgain.getText().toString());
				
				if (isOrganisation) {
					jsonObject.put("isOrganisation", 1);
				} else {
					jsonObject.put("isOrganisation", 0);
				}
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
				Toast.makeText(RegisterActivity.this, getText(R.string.server_error), Toast.LENGTH_SHORT).show();
			} else {
				try {
					switch (result.getInt("serverCode")) {
						case 0:
							username.setError(getText(R.string.empty_field));
							username.requestFocus();
						break;
						case 1:
							username.setError(getText(R.string.username_exists));
							username.requestFocus();
						break;
						case 2:
							password.setError(getText(R.string.password_not_match));
							password.requestFocus();
							break;
						case 3:
							email.setError(getText(R.string.email_exists));
							email.requestFocus();
							break;
						case 4:
							email.setError(getText(R.string.not_valid_email));
							email.requestFocus();
							break;
						case 10:
							if (isOrganisation) {
								Intent registerOrganisationIntent = new Intent(RegisterActivity.this, RegisterOrganisationActivity.class);
								registerOrganisationIntent.putExtra("username", username.getText().toString());
								registerOrganisationIntent.putExtra("password", password.getText().toString());
								registerOrganisationIntent.putExtra("email", email.getText().toString());
								startActivity(registerOrganisationIntent);
							} else {
								Intent registerPersonalIntent = new Intent(RegisterActivity.this, RegisterPersonalActivity.class);
								registerPersonalIntent.putExtra("username", username.getText().toString());
								registerPersonalIntent.putExtra("password", password.getText().toString());
								registerPersonalIntent.putExtra("email", email.getText().toString());
								startActivity(registerPersonalIntent);
							}
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

