package org.chinguyen.service;

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

@Service("customerService")
@Transactional
public class CustomerService {

	protected static Logger logger = Logger.getLogger("service");

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public List<Customer> getAll() {
		logger.debug("Retrieving all Customers");
		System.out.println("Retrieving all Customers");
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Customer");

		// Retrieve all
		return query.list();
	}

	public List<Customer> retrieveUsersByPage(Integer page, Integer rows, String orderBy,
			String sord) {
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Customer.class);
		if ("DESC".equalsIgnoreCase(sord)) {
			criteria.addOrder(Order.desc(orderBy));
		} else {
			criteria.addOrder(Order.asc(orderBy));
		}

		criteria.setFirstResult((page-1)*rows);
		criteria.setMaxResults(rows);
		return criteria.list();
	}
	
	public Integer countCustomer(){
		Session session = sessionFactory.getCurrentSession();
		Integer count = (Integer) session.createCriteria(Customer.class).setProjection(Projections.rowCount()).uniqueResult();
		if (count == null)
			return 0;
		return count;
	}
	
}
