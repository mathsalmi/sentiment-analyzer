package snet.model.repositories;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import snet.model.entities.User;

@Repository
@Transactional(readOnly = true)
public class UserRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public User findByUsername(String username) throws Exception {
		Session session = sessionFactory.getCurrentSession();

		Query q = session.createQuery("select u from User u where u.username=:username");
		q.setString("username", username);

		return (User) q.uniqueResult();
	}
}
