package webmini.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import webmini.common.QueryParam;
import webmini.model.UserDetails;
import webmini.service.FilterParam;

/**
 * Data access object that manages the access for {@link webmini.model.UserDetails}.
 * Contains hibernate specific logic for loading and saving UserDetails from persistant storage.
 */
public interface UserDao {

	/**
 	 * @return User count
 	 */
	long getCount();

	UserDetails getUser(int id);
	/**
	 * Find user with specific id
	 * @param name id to fetch
	 * @return UserDetails record
	 */
	UserDetails getUser(String name);

	int saveUser(UserDetails user);

	/**
	 * Retrieves a list of list of rows from the table 
	 * ordered by the {@value SortParam.property} 
	 * @param param 
	 * @return
	 */
	List<UserDetails> find(QueryParam param);

	/**
	 * Returns the number of rows returned by the filter.
	 * Used by SortableDataProvider during pagination.
	 * 
	 * @param filterState 
	 * @return results have this email address partially matched
	 */
	long getCount(FilterParam<String> filterState);

	/**
	 * Queries the users and applies a single property filter
	 * on an attribute that must be present and partially match.
	 * 
	 * Used for partial email searches. 
	 * 
	 * @param param
	 * @param filterState
	 * @return
	 */
	List<UserDetails> find(QueryParam param, FilterParam<String> filterState);

	/**
	 * Email attribute is considered a candidate key.
	 * @param email
	 * @return Returns tuple of row containing the email address.
	 */
	UserDetails getUserByEmail(String email);

}
