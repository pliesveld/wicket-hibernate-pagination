package webmini.service;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webmini.dao.UserDao;
import webmini.model.UserDetails;
import webmini.model.test.UserGenerator;
import webmini.users.web.GenerateRecords;

@Service
public class GenerateUserRecords {
    final static Logger LOG = LogManager.getLogger(GenerateUserRecords.class);

    @Autowired
    UserDao userDao;

    /**
     * Generates records to populate the User table.
     * @param num number of records to add.
     */
    public void create(int num)
    {
        LOG.info("Generating Users #: " + num);
        UserDetails user = UserGenerator.randomizedUser();
        LOG.info("Generated: " + user);
        userDao.saveUser(user);
        int cnt = 0;
        do {
            user = UserGenerator.randomizedUser();
            userDao.saveUser(user);
            cnt++;
        } while(num-- > 0);

        LOG.info("+ plus " + cnt + " more.");


    }
}
