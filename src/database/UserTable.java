package database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class UserTable {
	
	public static final String TABLE_NAME  = "users";
	
	public static class UserColumns implements BaseColumns {
		public static final String FIRST_NAME = "first_name";
		public static final String LAST_NAME = "last_name";
		public static final String POSITION = "position";
		public static final String BUSINESS_PHONE = "business_phone";
		public static final String PERSONAL_PHONE = "personal_phone";
		public static final String EMAIL = "email";
		public static final String SKYPE = "skype";
		public static final String FACEBOOK = "facebook";
	}
	
	public static void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + UserTable.TABLE_NAME + " (");
		sb.append(UserColumns._ID + " INTEGER PRIMARY KEY, ");
		sb.append(UserColumns.FIRST_NAME + " TEXT NOT NULL, ");
		sb.append(UserColumns.LAST_NAME + " TEXT NOT NULL, ");
		sb.append(UserColumns.POSITION + " TEXT NOT NULL, ");
		sb.append(UserColumns.BUSINESS_PHONE + " TEXT NOT NULL, ");
		sb.append(UserColumns.PERSONAL_PHONE + " TEXT NOT NULL, ");
		sb.append(UserColumns.EMAIL + " TEXT NOT NULL, ");
		sb.append(UserColumns.SKYPE + " TEXT NOT NULL, ");
		sb.append(UserColumns.FACEBOOK + " TEXT NOT NULL ");
		sb.append(");");
		
		db.execSQL(sb.toString());
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		UserTable.onCreate(db);
	}

}