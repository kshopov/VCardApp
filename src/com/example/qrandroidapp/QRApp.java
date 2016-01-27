package com.example.qrandroidapp;

import database.DataManagerImpl;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

public class QRApp extends Application {
	
	private DataManagerImpl dataManager = null;
	private ConnectivityManager connectivityManager = null;
	
	public DataManagerImpl getDataManager() {
		return this.dataManager;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		dataManager = new DataManagerImpl(this);
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
	public boolean connectivityPresent() {
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if((networkInfo != null) && (networkInfo.getState() != null)) {
			return networkInfo.getState().equals(State.CONNECTED);
		}
		return false;
	}
	
	
}
