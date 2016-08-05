package com.baeldung.users.web;

import com.baeldung.dao.UserDao;
import com.baeldung.service.GenerateUserRecords;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RangeTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class GenerateRecords extends WebPage {
    final static Logger LOG = LogManager.getLogger(GenerateRecords.class);

    @SpringBean
    private UserDao userDao;

    @SpringBean
    private GenerateUserRecords generateUserRecords;

    int count;

    public GenerateRecords() {
        setVersioned(false);
        Form<?> form = new Form<Void>("form") {
            @Override
            protected void onSubmit() {
                Component child = this.get("count");
                Object model = child.getDefaultModelObject();

                if (model instanceof Integer) {
                    Integer count = (Integer) model;
                    LOG.info("counting:" + count);
                    generateUserRecords.create(count);
                }
            }
        };

        form.add(new RangeTextField<Integer>("count", new PropertyModel<>(this, "count"))
                .setMinimum(1)
                .setMaximum(600)
                .setRequired(true));
        add(form);
        add(new FeedbackPanel("feedback"));
    }
}
