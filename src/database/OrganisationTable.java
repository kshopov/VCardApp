package database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class OrganisationTable {
	
	//this table will store scanned data of different organisations
	//something like history?
	
	public static final String TABLE_NAME = "organisations";
	
	public static class OrganisationColumns implements BaseColumns {
		public static final String ACCOUNT_ID = "account_id";
		public static final String ORGANISATION_NAME = "organisation_name";
		public static final String ORGANISATION_EMAIL = "organisation_email";
		public static final String ORGANISATION_ADDRESS = "organisation_address";
		public static final String ORGANISATION_WEB_PAGE = "organisation_web_page";
		public static final String ORGANISATION_PHONE = "organisation_phone";
		public static final String ORGANISATION_MOBILE = "organisation_mobile";
		public static final String ORGANISATION_FAX = "organisation_fax";
		public static final String ORGANISATION_COUNTRY = "organisation_country";
		public static final String ORGANISATION_CITY = "organisation_city";
		public static final String ORGANISATION_BRANCH = "organisation_branch";
		public static final String ORGANISATION_GPS_LONGTITUDE = "organisation_gps_longtitude";
		public static final String ORGANISATION_GPS_LATITUDE = "organisation_gps_latitude";
	}
	
	public static void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + OrganisationTable.TABLE_NAME + " (");
		sb.append(OrganisationColumns._ID + " INTEGER PRIMARY KEY, ");
		sb.append(OrganisationColumns.ACCOUNT_ID + " INT NOT NULL DEFAULT 0, ");
		sb.append(OrganisationColumns.ORGANISATION_NAME + " TEXT NOT NULL, ");
		sb.append(OrganisationColumns.ORGANISATION_EMAIL + " TEXT NOT NULL, ");
		sb.append(OrganisationColumns.ORGANISATION_ADDRESS + " TEXT NOT NULL, ");
		sb.append(OrganisationColumns.ORGANISATION_WEB_PAGE + " TEXT NOT NULL, ");
		sb.append(OrganisationColumns.ORGANISATION_PHONE + " TEXT NOT NULL, ");
		sb.append(OrganisationColumns.ORGANISATION_MOBILE + " TEXT NOT NULL, ");
		sb.append(OrganisationColumns.ORGANISATION_FAX + " TEXT NOT NULL, ");
		sb.append(OrganisationColumns.ORGANISATION_COUNTRY + " TEXT NOT NULL, ");
		sb.append(OrganisationColumns.ORGANISATION_CITY + " TEXT NOT NULL, ");
		sb.append(OrganisationColumns.ORGANISATION_BRANCH + " TEXT NOT NULL, ");
		sb.append(OrganisationColumns.ORGANISATION_GPS_LONGTITUDE + " REAL NOT NULL, ");
		sb.append(OrganisationColumns.ORGANISATION_GPS_LATITUDE + " REAL NOT NULL ");
		sb.append(");");
		
		db.execSQL(sb.toString());
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + OrganisationTable.TABLE_NAME);
		OrganisationTable.onCreate(db);
	}
}
