package org.chinguyen.service;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.chinguyen.domain.Customer;
import org.chinguyen.domain.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

@Service("entityService")
@Transactional
public class EntityDAO<T extends Serializable> {

	protected static Logger logger = Logger.getLogger("service");

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public List<T> getAll(Class<T> clazz) {
		logger.debug("Retrieving all entities");
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM " + clazz.getName());

		// Retrieve all
		return query.list();
	}

	public List<T> retrieveEntitiesByPage(Integer page, Integer rows, String orderBy,
			String sord, Class<T> clazz) {
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(clazz);
		if ("DESC".equalsIgnoreCase(sord)) {
			criteria.addOrder(Order.desc(orderBy));
		} else {
			criteria.addOrder(Order.asc(orderBy));
		}

		criteria.setFirstResult((page-1)*rows);
		criteria.setMaxResults(rows);
		return criteria.list();
	}
	
	public Integer count(Class<T> clazz){
		Session session = sessionFactory.getCurrentSession();
		Integer count = (Integer) session.createCriteria(clazz).setProjection(Projections.rowCount()).uniqueResult();
		if (count == null)
			return 0;
		return count;
	}
	
	public T get(Class<T> clazz, Long id) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Retrieve existing entity first
		T e = (T) session.get(clazz, id);

		return e;
	}
	
}
