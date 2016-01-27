package database;

import java.util.List;

public interface Dao<T> {
	
	long save(T type);
	long update(T type);
	long delete(T type);
	T get(long id);
	List<T> getAll();
	
}
