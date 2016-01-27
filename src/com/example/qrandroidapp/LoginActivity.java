package com.example.qrandroidapp;

import http.HttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import utils.Validator;
import model.Account;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	private static String URL = "http://parite.bg/mobile_login.php";
	private static String USER_PREFERENCES_FILE_NAME = "user_data";
	private ProgressDialog pd = null;
	private EditText username = null;
	private EditText password = null;
	
	private Account account = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		account = new Account();
	}
	
	public void login(View v) {
		if (validate()) {
			pd = ProgressDialog.show(this, getText(R.string.sending_data), "");
			SendData sendData = new SendData();
			sendData.execute(URL);
		}
	}
	
	private boolean validate() {
		if (Validator.isEmpty(username)) {
			username.setError(getText(R.string.empty_field));
			return false;
		} else if (Validator.isEmpty(password)) {
			password.setError(getText(R.string.empty_field));
			return false;
		}
		
		return true;
	}
	
	private void saveUserData() {
		SharedPreferences userData = getSharedPreferences(USER_PREFERENCES_FILE_NAME, 0);
		Editor edit = userData.edit();
		edit.putInt("id", account.getId());
		edit.putString("username", account.getUsername());
		edit.putString("email", account.getEmail());
		edit.commit();
	}
	
	private class SendData extends AsyncTask<String, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... params) {
			JSONObject jsonObject = new JSONObject();
			JSONObject results = new JSONObject();
			String url = params[0];
			try {
				jsonObject.put("username", username.getText().toString());
				jsonObject.put("password", password.getText().toString());
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
			results = HttpClient.SendHttpPost(url, jsonObject);
			
			return results;
		}
		
		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			if (result == null) {
				Toast.makeText(LoginActivity.this, getText(R.string.server_error), Toast.LENGTH_LONG).show();
			} else {
				try {
					int resultCode = result.getInt("serverCode");
					if (resultCode != 0) {
						account.setId(result.getInt("id"));
						account.setEmail(result.getString("email"));
						account.setUsername(result.getString("username"));
						saveUserData();
						AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
						dialog.setTitle(getText(R.string.successful_login));
						dialog.setNeutralButton(getString(R.string.main), new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
								startActivity(mainIntent);
							}
						});
						dialog.show();
					} else {
						Toast.makeText(LoginActivity.this, getText(R.string.wrong_user_pass), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
			pd.hide();
		}
		
	}
}
