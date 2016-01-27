package com.example.qrandroidapp;

import model.Organisation;
import model.User;

import com.example.qrandroidapp.R;

import adapters.GaleryAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private QRApp app = null;
	private static final int ZBAR_SCANNER_REQUEST = 0;
	private static final int ZBAR_QR_SCANNER_REQUEST = 1;
	private static final String SHARE_URL = "http://google.bg";

	public static final String PASSWORD = "pref_password";

	private SharedPreferences settings = null;

	private Organisation organisation = null;
	private User user = null;

	ImageView selectedImage;
	TextView imageDescription;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		app = (QRApp) getApplication();
		organisation = app.getDataManager().getOrganisation(1);
		user = app.getDataManager().getUser(1);
		
		Gallery gallery = (Gallery) findViewById(R.id.gallery1);
		selectedImage = (ImageView) findViewById(R.id.imageView1);
		gallery.setSpacing(50);
		gallery.setAdapter(new GaleryAdapter(this, organisation, user));

		settings = PreferenceManager.getDefaultSharedPreferences(this);
		final String password = settings.getString(PASSWORD, "");
		if (!password.equals("")) {
			final AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("Enter your password");

			// Set an EditText view to get user input
			final EditText input = new EditText(this);
			input.setInputType(InputType.TYPE_CLASS_TEXT
					| InputType.TYPE_TEXT_VARIATION_PASSWORD);
			alert.setView(input);

			alert.setPositiveButton(getText(R.string.ok),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}

					});
			alert.setNegativeButton(getText(R.string.cancel),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							finish();
						}
					});
			final AlertDialog dialog = alert.create();
			dialog.show();

			dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							if (password.equals(input.getText().toString())) {
								dialog.dismiss();
							} else {
								input.setError(getText(R.string.wrong_pass));
							}
						}
					});
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ZBAR_SCANNER_REQUEST:
		case ZBAR_QR_SCANNER_REQUEST:
			if (resultCode == RESULT_OK) {
				String result = data.getStringExtra(ZBarConstants.SCAN_RESULT);
				Intent resultIntent = new Intent(MainActivity.this,
						ScanQRResultsActivity.class);
				resultIntent.putExtra("results", result);
				startActivity(resultIntent);
			} else if (resultCode == RESULT_CANCELED && data != null) {
				String error = data.getStringExtra(ZBarConstants.ERROR_INFO);
				if (!TextUtils.isEmpty(error)) {
					Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
				}
			}
			break;
		}
	}

	public void scan(View v) {
		if (isCameraAvailable()) {
			Intent intent = new Intent(this, ZBarScannerActivity.class);
			startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
		} else {
			Toast.makeText(this, "Rear Facing Camera Unavailable",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void share(View v) {
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, MainActivity.SHARE_URL);
		sendIntent.setType("text/plain");
		startActivity(sendIntent);
	}

	public void openMenu(View v) {
		Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
	}

	private boolean isCameraAvailable() {
		PackageManager pm = getPackageManager();
		return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}

}