package org.chinguyen.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.chinguyen.domain.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

@Service("userService")
@Transactional
public class UserService {

	protected static Logger logger = Logger.getLogger("service");

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public List<User> getAll() {
		logger.debug("Retrieving all Users");
		System.out.println("Retrieving all Users");
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM User");

		// Retrieve all
		return query.list();
	}

	public List<User> retrieveUsersByPage(Integer page, Integer rows, String orderBy,
			String sord) {
		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		if ("DESC".equalsIgnoreCase(sord)) {
			criteria.addOrder(Order.desc(orderBy));
		} else {
			criteria.addOrder(Order.asc(orderBy));
		}

		criteria.setFirstResult((page-1)*rows);
		criteria.setMaxResults(rows);
		return criteria.list();
	}
	
	public Integer countUsers(){
		Session session = sessionFactory.getCurrentSession();
		Integer count = (Integer) session.createCriteria(User.class).setProjection(Projections.rowCount()).uniqueResult();
		if (count == null)
			return 0;
		return count;
	}

	public User get(Long id) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Retrieve existing user first
		User user = (User) session.get(User.class, id);

		return user;
	}
	
	public User findByUserName(String userName) {
		Session session = sessionFactory.getCurrentSession();

		// Retrieve existing user first
		User user = (User) session.createCriteria(User.class)
				.add(Restrictions.eq("username", userName)).uniqueResult();

		return user;
	}

	public Boolean add(User user) {
		Session session = sessionFactory.getCurrentSession();
		Long savedId = (Long) session.save(user);
		if (savedId == null)
			return false;
		return true;
	}

	public Boolean update(User user) {
		logger.debug("Editing existing user");

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();

		// Retrieve existing user via id
		// tren form ko co ID truyen xuong, do do phai lay user base on username
		Query q = session.createQuery("from user where username = :username ");
		User existingUser = (User) q.setParameter("username", user.getUsername()).uniqueResult();

		// Assign updated values to this user
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(existingUser.getLastName());
		// existingUser.setRole(existingUser.getRole());

		// Save updates
		session.save(existingUser);
		return true;
	}

	public Boolean delete(User user) {
		logger.debug("Editing existing user");

		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		session.delete(user);

		User deletedUser = (User) session.get(User.class, user.getId());
		if (deletedUser != null)
			return false;

		return true;
	}
}
