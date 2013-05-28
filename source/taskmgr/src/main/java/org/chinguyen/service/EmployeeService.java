/**
 * Tat ca nhung class service co the viet thanh 1 class thoi
 * getAll(T) : T la ten Entity, se return List(T)
 * Tuong tu cho retrievebyPage
 */
package org.chinguyen.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.chinguyen.domain.Employee;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

@Service("employeeService")
@Transactional
public class EmployeeService {

	protected static Logger logger = Logger.getLogger("service");

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public List<Employee> getAll() {
		logger.debug("Retrieving all Employees");
		System.out.println("Retrieving all Employees");
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Employee");

		// Retrieve all
		return query.list();
	}

	public List<Employee> retrieveUsersByPage(Integer page, Integer rows, String orderBy,
			String sord) {
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Employee.class);
		if ("DESC".equalsIgnoreCase(sord)) {
			criteria.addOrder(Order.desc(orderBy));
		} else {
			criteria.addOrder(Order.asc(orderBy));
		}

		criteria.setFirstResult((page-1)*rows);
		criteria.setMaxResults(rows);
		return criteria.list();
	}
	
	public Integer countEmployee(){
		Session session = sessionFactory.getCurrentSession();
		Integer count = (Integer) session.createCriteria(Employee.class).setProjection(Projections.rowCount()).uniqueResult();
		if (count == null)
			return 0;
		return count;
	}
	
}
