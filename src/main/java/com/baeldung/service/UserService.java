package com.baeldung.service;

import com.baeldung.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * I am not sure of the purpose of this separation.
 * <p/>
 * I think this is supposed simply the finding of DAOs once more models have been added to the system.
 * Or to differentiate between different types of DAOS.  For example; if, in adddition to the hibernate DAO I currently have,
 * there was another UserDao that specialized in XML files.  Then the service layer could provide a centralized place
 * for the client code to instantiate the DAO objects of a particular type.
 * <p/>
 * But at the moment, there is only one DAO class, @see webmini.model.UserDao
 */

@Component
public class UserService {
    @Autowired
    private UserDao userDao;

    public UserService() {}

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
