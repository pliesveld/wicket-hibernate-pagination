package webmini.users.web;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.form.TextField;

import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.IModel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.DataGridView;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.PropertyPopulator;


import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm;
import org.apache.wicket.spring.injection.annot.SpringBean;

import webmini.common.QueryParam;
import webmini.dao.UserDao;
import webmini.model.UserDetails;
import webmini.service.EmailFilter;
import webmini.service.FilterParam;

public class ViewRecords extends WebPage
{
    private static final long serialVersionUID = 1L;

    final static Logger LOG = LogManager.getLogger(ViewRecords.class);

    @SpringBean
    private UserDao userDao;

    public ViewRecords()
    {
        SortableUserProvider dp = new SortableUserProvider();

        List<IColumn<UserDetails, String>> columns = new ArrayList<>();

        columns.add(new PropertyColumn<UserDetails,String>(new Model<>("id"),"id","id"));
        columns.add(new PropertyColumn<UserDetails,String>(new Model<>("name"),"name","name"));
        columns.add(new PropertyColumn<UserDetails,String>(new Model<>("email"),"email","email")); /* TODO: TextFilteredPropertyColumn */
        columns.add(new PropertyColumn<UserDetails,String>(new Model<>("role"),"role","role"));

        final DataTable<UserDetails,String> dataTable = new DefaultDataTable<>("table", columns, dp,3);

        dataTable.setOutputMarkupId(true);
        FilterForm<FilterParam<String>> filterForm = new FilterForm<FilterParam<String>>("filterForm",dp);

        /* underlying type of filterstate has field FilterParam that has field value*/
        filterForm.add(new TextField<>("filterEmail", PropertyModel.of(dp,"filterState.value")));

        add(filterForm);


        FilterToolbar filterToolbar = new FilterToolbar(dataTable,filterForm);
        dataTable.addTopToolbar(filterToolbar);


        filterForm.add(dataTable);
    }


    /**
     *
     * SortableDataProvider has methods for retriving an iterator range
     * from a datasource, and builtin in model state for sorting data.
     */
    private class SortableUserProvider extends SortableDataProvider<UserDetails,String>
        implements IFilterStateLocator<FilterParam<String>>
    {

        private static final long serialVersionUID = 1L;
        private FilterParam<String> emailFilter = new EmailFilter("");

        public SortableUserProvider()
        {
            setSort("name",SortOrder.ASCENDING);
        }

        @Override
        public Iterator<? extends UserDetails> iterator(long first, long count)
        {
            String email = emailFilter.getValue();

            QueryParam param = new QueryParam(first,count,getSort());

            if(email == null || email == "")
            {
                return userDao.find(param).iterator();
            } else {
                return userDao.find(param,getFilterState()).iterator();
            }
        }

        public long size()
        {
            String email = emailFilter.getValue();
            if(email == null || email == "")
                return userDao.getCount();
            else
                return userDao.getCount(emailFilter);
        }

        /**
         * LoadDetachableModel has methods for clearing our references to the model type
         * being held.  After the request cycle, when the data is no longer used, this
         * allows us a chance to prevent the class from being serialized with the rest of
         * the page.
         */
        @Override
        public IModel<UserDetails> model(final UserDetails userDetails)
        {
            final Integer id = userDetails.getId();
            return new LoadableDetachableModel<UserDetails>(userDetails) {

                @Override
                protected UserDetails load() {
                    //LOG.info(String.format("Load(%d) = %s",id,user));
                    return userDao.getUser(id);
                }
            };

        }

        @Override
        public void detach() {
            super.detach();
            LOG.info("detach()");
        }

        @Override
        public FilterParam<String> getFilterState() {
            return emailFilter;
        }

        @Override
        public void setFilterState(FilterParam<String> state) {
            this.emailFilter = state;

        }
    }

}
//prior techniques for rendering tables
/*
 * The second attempt uses DataGridView which differs from
 * DataView in that populateItem is no longer called.  Columns identifiers are
 * created and passed into the DataGridView.  The markup resebles a table entry:


<!-- markup for DataGridView -->
<table>
    <tr wicket:id="rows">
        <td wicket:id="cells">
            <span wicket:id="cell">cell content goes here</span>
        </td>
    </tr>
</table>



List<ICellPopulator<UserDetails>> columns = new ArrayList<>();
columns.add(new PropertyPopulator<UserDetails>("id"));
columns.add(new PropertyPopulator<UserDetails>("name"));
columns.add(new PropertyPopulator<UserDetails>("email"));
columns.add(new PropertyPopulator<UserDetails>("role"));

final DataGridView<UserDetails> dataView = new DataGridView<UserDetails>("rows", columns, dp);
dataView.setItemsPerPage(3);
add(dataView);
*/


/*
 * First attempt implementing a sortable view using the DataView
 * Component.  Each label is created inside the populateItem().

final DataView<UserDetails> dataView = new DataView<UserDetails>("rows",dp,3)
{
    public void populateItem(final Item<UserDetails> item)
    {
            final UserDetails user = item.getModelObject();
            item.add(new Label("id", user.getId()));
            item.add(new Label("name", user.getName()));
            item.add(new Label("email", user.getEmail()));
            item.add(new Label("role", user.getRole()));
    }
};

/*      DataGridView and DataTable incorporate Paging navigators into their components.
The prior attempts to display pagination required the PagingNavigator

Markup:

<!--
<div wicket:id="pagingNavigator"></div>
-->

add(new PagingNavigator("pagingNavigator",dataView));

dataView.setItemsPerPage(3);
add(dataView);

<table>
<!-- Markup for OrderByBorder: -->
<tr>
    <th wicket:id="orderById">ID</th>
    <th wicket:id="orderByName">username</th>
    <th wicket:id="orderByEmail">email</th>
    <th>role</th>
</tr>

<!-- Markup for Items populated from populateItem  -->
<tr wicket:id="rows">
    <td><span wicket:id="id">[ID]</span></td>
    <td><span wicket:id="name">[NAME]</span></td>
    <td><span wicket:id="email">[EMAIL]</span></td>
    <td><span wicket:id="role">[ROLE]</span></td>
</tr>
</table>


*/

/*
    wicket components are bind the markup labels with the sorted
    data provider


add(new OrderByBorder("orderById","id",dp)
{
    private static final long serialVersionUID = 1L;

    @Override
    protected void onSortChanged()
    {
        dataView.setCurrentPage(0);
    }
});
add(new OrderByBorder("orderByName","name",dp)
{
    private static final long serialVersionUID = 1L;

    @Override
    protected void onSortChanged()
    {
        dataView.setCurrentPage(0);
    }
});
add(new OrderByBorder("orderByEmail","email",dp)
{
    private static final long serialVersionUID = 1L;

    @Override
    protected void onSortChanged()
    {
        dataView.setCurrentPage(0);
    }
}); */

