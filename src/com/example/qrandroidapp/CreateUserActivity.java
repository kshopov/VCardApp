package com.example.qrandroidapp;

import utils.Validator;
import model.User;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateUserActivity extends Activity {
	
	private EditText firstName = null;
	private EditText lastName = null;
	private EditText position = null;
	private EditText businessPhone = null;
	private EditText personalPhone = null;
	private EditText email = null;
	private EditText skype = null;
	private EditText facebook = null;
	
	private QRApp app = null;
	
	private User user = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_personal);
		
		firstName = (EditText) findViewById(R.id.first_name);
		lastName = (EditText) findViewById(R.id.last_name);
		position = (EditText) findViewById(R.id.position);
		businessPhone = (EditText) findViewById(R.id.business_phone);
		personalPhone = (EditText) findViewById(R.id.personal_phone);
		email = (EditText) findViewById(R.id.email);
		skype = (EditText) findViewById(R.id.skype);
		facebook = (EditText) findViewById(R.id.facebook);
		
		app = (QRApp) getApplication();
		
		user = new User();
	}
	
	public void saveUserProfile(View v) {
		user.setFirstName(firstName.getText().toString());
		user.setLastName(lastName.getText().toString());
		user.setPostion(position.getText().toString());
		user.setBusinessPhone(businessPhone.getText().toString());
		user.setPersonalPhone(personalPhone.getText().toString());
		user.setEmail(email.getText().toString());
		user.setSkype(skype.getText().toString());
		user.setFacebook(facebook.getText().toString());
		
		if (validate() && (app.getDataManager().saveUser(user) > 0)) {
			Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Not success", Toast.LENGTH_SHORT).show();
		}
	}
	
	private boolean validate() {
		if(!Validator.isValidEmail(email)) {
			email.setError(getString(R.string.not_valid_email));
			email.requestFocus();
			return false;
		}
		return true;
	}
}
