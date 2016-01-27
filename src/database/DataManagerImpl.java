package database;

import java.util.List;

import model.Organisation;
import model.User;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataManagerImpl implements DataManager {
	
	private Context context = null;
	
	private SQLiteDatabase db = null;
	
	private OrganisationDao organisationDao = null;
	private UserDao userDao = null;
	
	public DataManagerImpl(Context context) {
		this.context = context;
		
		SQLiteOpenHelper openHelper = new QRDBOpenHelper(this.context);
		db = openHelper.getWritableDatabase();
		
		organisationDao = new OrganisationDao(db);
		userDao = new UserDao(db);
	}

	@Override
	public Organisation getOrganisation(long organisationId) {
		return organisationDao.get(organisationId);
	}

	@Override
	public List<Organisation> getAllOrganisations() {
		return organisationDao.getAll();
	}

	@Override
	public long saveOrganisation(Organisation organisation) {
		return organisationDao.save(organisation);
	}

	@Override
	public long deleteOrganisation(Organisation organisation) {
		return organisationDao.delete(organisation);
	}

	@Override
	public long updateOrganisation(Organisation organisation) {
		return organisationDao.update(organisation);
	}
	
	//users table

	@Override
	public User getUser(long userId) {
		return userDao.get(userId);
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.getAll();
	}

	@Override
	public long saveUser(User user) {
		return userDao.save(user);
	}

	@Override
	public long deleteUser(User user) {
		return userDao.delete(user);
	}

	@Override
	public long updateUser(User user) {
		return userDao.update(user);
	}

}
