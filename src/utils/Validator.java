package utils;

import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

public class Validator {
	
	public static final boolean isValidEmail(EditText email) {
		if(Validator.isEmpty(email)) {
			Log.i("VALID", "IS VALID: " + Validator.isEmpty(email));
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches();
		}
	}
	
	public static final boolean isEmpty(EditText text) {
		return TextUtils.isEmpty(text.getText().toString());
	}
	
	public static final boolean isSamevalue(EditText firsValue, EditText secondValue) {
		if(Validator.isEmpty(firsValue) || Validator.isEmpty(secondValue)) {
			return false;
		}
		return TextUtils.equals(firsValue.getText(), secondValue.getText());
	}
	
}
