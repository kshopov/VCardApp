package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QRDBOpenHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	
	Context context;
	
	public QRDBOpenHelper(Context context) {
		super(context, DataConstants.DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		OrganisationTable.onCreate(db);
		UserTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
