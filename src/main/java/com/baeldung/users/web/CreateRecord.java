package com.baeldung.users.web;

import com.baeldung.dao.UserDao;
import com.baeldung.model.UserDetails;
import com.baeldung.model.UserRole;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.io.IClusterable;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class CreateRecord extends WebPage {
    final static Logger LOG = LoggerFactory.getLogger(CreateRecord.class);

    @SpringBean
    private UserDao userDao;

    public CreateRecord() {
        setVersioned(false);
        final Input input = new Input();
        setDefaultModel(new CompoundPropertyModel<>(input));
        Form<?> form = new Form("form") {
            @Override
            protected void onSubmit() {
                info("adding user: " + input);
                UserDetails user = new UserDetails();
                user.setName(input.userName);
                user.setEmail(input.userEmail);
                user.setRole(input.userRole);

                userDao.saveUser(user);
            }
        };
        add(form);

        TextField<String> username = new TextField<>("userName");
        username.setRequired(true);
        username.add(new UserNameValidator());

        EmailTextField email = new EmailTextField("userEmail");
        email.setRequired(true);
        email.add(new EmailAddressValidator());

        form.add(email);
        form.add(username);
        form.add(new DropDownChoice<UserRole>("userRole",
                Arrays.asList(UserRole.values())));
        add(new FeedbackPanel("feedback"));
    }

    private static class Input implements IClusterable {
        String userName;
        String userEmail;
        UserRole userRole = UserRole.NORMAL;

        @Override
        public String toString() {
            return "user = " + userName + ", email = " + userEmail + ", role = " + userRole;
        }
    }

    private class UserNameValidator implements IValidator<String> {
        @Override
        public void validate(IValidatable<String> validatable) {
            final String field = validatable.getValue();
            UserDetails user = userDao.getUser(field);
            if (user != null) {
                ValidationError error = new ValidationError();
                error.setMessage("user-name-taken");
                validatable.error(error);
            }
        }
    }

    private class EmailAddressValidator implements IValidator<String> {
        @Override
        public void validate(IValidatable<String> validatable) {
            final String field = validatable.getValue();
            UserDetails user = userDao.getUserByEmail(field);
            if (user != null) {
                ValidationError error = new ValidationError();
                error.setMessage("email-address-taken");
                validatable.error(error);
            }
        }
    }
}
