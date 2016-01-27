package database;

import java.util.ArrayList;
import java.util.List;

import database.OrganisationTable.OrganisationColumns;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import model.Organisation;

public class OrganisationDao implements Dao<Organisation> {
	
	private SQLiteDatabase db = null;
	private SQLiteStatement insertStatement = null;
	
	private static final String INSERT = 
			"INSERT INTO " + OrganisationTable.TABLE_NAME + "("
				+ OrganisationColumns.ORGANISATION_NAME + ", "
				+ OrganisationColumns.ORGANISATION_EMAIL + ", "
				+ OrganisationColumns.ORGANISATION_ADDRESS + ", "
				+ OrganisationColumns.ORGANISATION_WEB_PAGE + ", "
				+ OrganisationColumns.ORGANISATION_PHONE + ", "
				+ OrganisationColumns.ORGANISATION_MOBILE + ", "
				+ OrganisationColumns.ORGANISATION_FAX + ", "
				+ OrganisationColumns.ORGANISATION_COUNTRY + ", "
				+ OrganisationColumns.ORGANISATION_CITY + ", "
				+ OrganisationColumns.ORGANISATION_BRANCH + ", "
				+ OrganisationColumns.ORGANISATION_GPS_LONGTITUDE + ", "
				+ OrganisationColumns.ORGANISATION_GPS_LATITUDE + ")"
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	public OrganisationDao(SQLiteDatabase db) {
		this.db = db;
		insertStatement = db.compileStatement(OrganisationDao.INSERT);
	}

	@Override
	public long save(Organisation organisation) {
		insertStatement.clearBindings();
		insertStatement.bindString(1, organisation.getName());
		insertStatement.bindString(2, organisation.getEmail());
		insertStatement.bindString(3, organisation.getAddress());
		insertStatement.bindString(4, organisation.getWebPage());
		insertStatement.bindString(5, organisation.getPhone());
		insertStatement.bindString(6, organisation.getMobile());
		insertStatement.bindString(7, organisation.getFax());
		insertStatement.bindString(8, organisation.getCountry());
		insertStatement.bindString(9, organisation.getCity());
		insertStatement.bindString(10, organisation.getBranch());
		insertStatement.bindDouble(11, organisation.getGpsLongtitude());
		insertStatement.bindDouble(12, organisation.getGpsLatitude());
		
		return insertStatement.executeInsert();
	}

	@Override
	public long update(Organisation organisation) {
		long result = 0;
		final ContentValues contentValues = new ContentValues();
		contentValues.put(OrganisationColumns.ORGANISATION_NAME, organisation.getName());
		contentValues.put(OrganisationColumns.ORGANISATION_EMAIL, organisation.getEmail());
		contentValues.put(OrganisationColumns.ORGANISATION_ADDRESS, organisation.getAddress());
		contentValues.put(OrganisationColumns.ORGANISATION_WEB_PAGE, organisation.getWebPage());
		contentValues.put(OrganisationColumns.ORGANISATION_PHONE, organisation.getPhone());
		contentValues.put(OrganisationColumns.ORGANISATION_MOBILE, organisation.getMobile());
		contentValues.put(OrganisationColumns.ORGANISATION_FAX, organisation.getFax());
		contentValues.put(OrganisationColumns.ORGANISATION_COUNTRY, organisation.getCountry());
		contentValues.put(OrganisationColumns.ORGANISATION_CITY, organisation.getCity());
		contentValues.put(OrganisationColumns.ORGANISATION_BRANCH, organisation.getBranch());
		contentValues.put(OrganisationColumns.ORGANISATION_GPS_LONGTITUDE, organisation.getGpsLongtitude());
		contentValues.put(OrganisationColumns.ORGANISATION_GPS_LATITUDE, organisation.getGpsLatitude());
		
		result = db.update(OrganisationTable.TABLE_NAME, contentValues, BaseColumns._ID + " = ?", 
				new String[] { String.valueOf(organisation.getId())});
		
		return result;
	}

	@Override
	public long delete(Organisation organisation) {
		long results = 0; 
		if(organisation.getId() > 0) {
			results = db.delete(OrganisationTable.TABLE_NAME, 
					BaseColumns._ID + " = ?",
					new String[] {String.valueOf(organisation.getId())});
		}
		return results;
	}

	@Override
	public Organisation get(long id) {
		Organisation organisation = null;
		Cursor c = db.query(OrganisationTable.TABLE_NAME,
				null,
				BaseColumns._ID + " = ?",
				new String[] {String.valueOf(id)},
				null, null, null);
		
		if(c.moveToFirst()) {
			organisation = buildOrganisationFromCursor(c);
		}
		
		return organisation;
	}

	@Override
	public List<Organisation> getAll() {
		List<Organisation> organisationsList = new ArrayList<Organisation>();
		Cursor c = db.query(OrganisationTable.TABLE_NAME,
				new String[] {
					BaseColumns._ID,
					OrganisationColumns.ACCOUNT_ID,
					OrganisationColumns.ORGANISATION_NAME,
					OrganisationColumns.ORGANISATION_EMAIL,
					OrganisationColumns.ORGANISATION_ADDRESS,
					OrganisationColumns.ORGANISATION_WEB_PAGE,
					OrganisationColumns.ORGANISATION_PHONE,
					OrganisationColumns.ORGANISATION_MOBILE,
					OrganisationColumns.ORGANISATION_FAX,
					OrganisationColumns.ORGANISATION_COUNTRY,
					OrganisationColumns.ORGANISATION_CITY,
					OrganisationColumns.ORGANISATION_BRANCH,
					OrganisationColumns.ORGANISATION_GPS_LONGTITUDE,
					OrganisationColumns.ORGANISATION_GPS_LATITUDE
				},
				null, null, null, null, null, null);
		if (c.moveToFirst()) {
			do {
				Organisation organisation = this.buildOrganisationFromCursor(c);
				if (organisation != null) {
					organisationsList.add(organisation);
				}
			} while (c.moveToNext());
		} else {
			return null;
		}
		
		if (!c.isClosed()) {
			c.close();
		}
		
		return organisationsList;
	}
	
	private Organisation buildOrganisationFromCursor(Cursor c) {
		Organisation organisation = null;
		if (c != null) {
			organisation = new Organisation();
			organisation.setId((int)c.getLong(0));
			organisation.setAccountId(c.getLong(1));
			organisation.setName(c.getString(2));
			organisation.setEmail(c.getString(3));
			organisation.setAddress(c.getString(4));
			organisation.setWebPage(c.getString(5));
			organisation.setPhone(c.getString(6));
			organisation.setMobile(c.getString(7));
			organisation.setFax(c.getString(8));
			organisation.setCountry(c.getString(9));
			organisation.setCity(c.getString(10));
			organisation.setBranch(c.getString(11));
			organisation.setGpsLongtitude(c.getDouble(12));
			organisation.setGpsLatitude(c.getDouble(13));
		}
		
		return organisation;
	}

}
