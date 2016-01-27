package database;

import java.util.List;

import model.Organisation;
import model.User;

public interface DataManager {
	
	//organisations
	public Organisation getOrganisation(long organisationId);
	public List<Organisation> getAllOrganisations();
	public long saveOrganisation(Organisation organisation);
	public long deleteOrganisation(Organisation organisation);
	public long updateOrganisation(Organisation organisation);
	
	//user
	public User getUser(long userId);
	public List<User> getAllUsers();
	public long saveUser(User user);
	public long deleteUser(User user);
	public long updateUser(User user);

}
