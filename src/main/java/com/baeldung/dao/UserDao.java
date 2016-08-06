package com.baeldung.dao;

import com.baeldung.common.QueryParam;
import com.baeldung.model.UserDetails;
import com.baeldung.common.FilterParam;

import java.util.List;

/**
 * Data access object that manages the access for {@link com.baeldung.model.UserDetails}.
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
     *
     * @param name id to fetch
     * @return UserDetails record
     */
    UserDetails getUser(String name);

    int saveUser(UserDetails user);

    /**
     * Retrieves a list of list of rows from the table
     * ordered by the {@value SortParam.property}
     *
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
     * <p/>
     * Used for partial email searches.
     *
     * @param param
     * @param filterState
     * @return
     */
    List<UserDetails> find(QueryParam param, FilterParam<String> filterState);

    /**
     * Email attribute is considered a candidate key.
     *
     * @param email
     * @return Returns tuple of row containing the email address.
     */
    UserDetails getUserByEmail(String email);

}
