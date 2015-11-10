package webmini.dao;

import java.io.Serializable;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.wicket.model.LoadableDetachableModel;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Session;

// TODO: WIP - generalize Detachable model for hibernate dao

/*
public class HibernateLoadableModel<T> extends LoadableDetachableModel<T> 
{

    final static Logger LOG = LogManager.getLogger(HibernateLoadableModel.class);
	private Class<T> entityClass;
	private IdentifierLoadAccess<T> loadByKey;
	private Serializable identifier;
	
	private UserDao userDao;
	
	public HibernateLoadableModel(UserDao userDao,T entity)
	{
		LOG.info("LodableModel:" + entity);
		this.userDao = userDao;
		Session session = userDao.getSession();
		
		loadByKey = session.byId(entityClass);
		entityClass = (Class<T>) entity.getClass();
		
		identifier = session.getIdentifier(entity);
		setObject(entity);
	}

	@Override
	protected T load() {
		T entity = null;
		LOG.info("load()");
		if(identifier != null)
		{
			Session session = userDao.getSession();
			session.load(entityClass, identifier);
		}

		return entity;
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		LOG.info("onDetach()");
		T entity = getObject();
		if(entity == null) return;
		
		identifier = userDao.getSession().getIdentifier(entity);
	}
}	
*/

