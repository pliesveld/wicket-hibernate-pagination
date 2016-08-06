package com.baeldung.service;

import com.baeldung.dao.UserDao;
import com.baeldung.model.UserDetails;
import com.baeldung.model.test.UserGenerator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerateUsersService {
    final static Logger LOG = LogManager.getLogger(GenerateUsersService.class);

    @Autowired
    UserDao userDao;

    /**
     * Generates records to populate the User table.
     *
     * @param num number of records to add.
     */
    public void create(int num) {
        LOG.info("Generating #{} users: " + num);
        UserDetails user = UserGenerator.randomizedUser();
        userDao.saveUser(user);
        int cnt = 0;
        do {
            user = UserGenerator.randomizedUser();
            userDao.saveUser(user);
            cnt++;
        } while (num-- > 0);
    }
}
