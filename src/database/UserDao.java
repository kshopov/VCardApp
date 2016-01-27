package database;

import java.util.ArrayList;
import java.util.List;

import model.User;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import database.UserTable.UserColumns;

public class UserDao implements Dao<User> {
	
	private static final String INSERT = 
			"INSERT INTO " + UserTable.TABLE_NAME + "("
				+ UserColumns.FIRST_NAME + ", "
				+ UserColumns.LAST_NAME + ", "
				+ UserColumns.POSITION + ", "
				+ UserColumns.BUSINESS_PHONE + ", "
				+ UserColumns.PERSONAL_PHONE + ", "
				+ UserColumns.EMAIL + ", "
				+ UserColumns.SKYPE + ", "
				+ UserColumns.FACEBOOK + ")"
					+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
	
	private SQLiteDatabase db = null;
	private SQLiteStatement insertStatement = null;
	
	public UserDao(SQLiteDatabase db) {
		this.db = db;
		insertStatement = db.compileStatement(INSERT);
	}

	@Override
	public long save(User user) {
		insertStatement.clearBindings();
		insertStatement.bindString(1, user.getFirstName());
		insertStatement.bindString(2, user.getLastName());
		insertStatement.bindString(3, user.getPostion());
		insertStatement.bindString(4, user.getBusinessPhone());
		insertStatement.bindString(5, user.getPersonalPhone());
		insertStatement.bindString(6, user.getEmail());
		insertStatement.bindString(7, user.getSkype());
		insertStatement.bindString(8, user.getFacebook());
		
		return insertStatement.executeInsert();
	}

	@Override
	public long update(User user) {
		long result = 0;
		ContentValues contentValues = new ContentValues();
		contentValues.put(UserColumns.FIRST_NAME, user.getFirstName());
		contentValues.put(UserColumns.LAST_NAME, user.getLastName());
		contentValues.put(UserColumns.POSITION, user.getPostion());
		contentValues.put(UserColumns.BUSINESS_PHONE, user.getBusinessPhone());
		contentValues.put(UserColumns.PERSONAL_PHONE, user.getPersonalPhone());
		contentValues.put(UserColumns.EMAIL, user.getEmail());
		contentValues.put(UserColumns.SKYPE, user.getSkype());
		contentValues.put(UserColumns.FACEBOOK, user.getFacebook());
		
		result = db.update(UserTable.TABLE_NAME, contentValues, BaseColumns._ID + " = ?",
				new String[] { String.valueOf(user.getId()) });
		
		return result;
	}

	@Override
	public long delete(User user) {
		long result = 0;
		if (user.getId() > 0) {
			result = db.delete(UserTable.TABLE_NAME,
					BaseColumns._ID + " = ?",
					new String[] {String.valueOf(user.getId())});
		}
		
		return result;
	}

	@Override
	public User get(long id) {
		User user = null;
		Cursor c = db.query(UserTable.TABLE_NAME,
				null,
				BaseColumns._ID + " = ?",
				new String[] {String.valueOf(id)},
				null, null, null);
		
		if(c.moveToFirst()) {
			user = buildUserFromCursor(c);
		}
		
		return user;
	}

	@Override
	public List<User> getAll() {
		List<User> usersList = new ArrayList<User>();
		Cursor c = db.query(UserTable.TABLE_NAME,
				new String[] { 
					BaseColumns._ID,
					UserColumns.FIRST_NAME,
					UserColumns.LAST_NAME,
					UserColumns.POSITION,
					UserColumns.BUSINESS_PHONE,
					UserColumns.PERSONAL_PHONE,
					UserColumns.EMAIL,
					UserColumns.SKYPE,
					UserColumns.FACEBOOK
				},
				null, null, null, null, null);
		
		if (c.moveToFirst()) {
			do {
				User user = this.buildUserFromCursor(c);
				if (user != null) {
					usersList.add(user);
				}
			} while (c.moveToNext());
		} else {
			return null;
		}
		
		if (!c.isClosed()) {
			c.close();
		}
		
		return usersList;
	}
	
	private User buildUserFromCursor(Cursor c) {
		User user = null;
		if (c != null) {
			user = new User();
			user.setId((int)c.getLong(0));
			user.setFirstName(c.getString(1));
			user.setLastName(c.getString(2));
			user.setPostion(c.getString(3));
			user.setBusinessPhone(c.getString(4));
			user.setPersonalPhone(c.getString(5));
			user.setEmail(c.getString(6));
			user.setSkype(c.getString(7));
			user.setFacebook(c.getString(8));
		}
		
		return user;
	}

}
