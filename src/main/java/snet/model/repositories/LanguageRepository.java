package snet.model.repositories;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import snet.model.entities.Language;

@Repository
@Transactional(readOnly = true)
public class LanguageRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public Language findById(String id) {
		Session session = sessionFactory.getCurrentSession();

		Query q = session.createQuery("select l from Language l where id=:id");
		q.setString("id", id);

		return (Language) q.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Language> listAllActive() {
		Session session = sessionFactory.getCurrentSession();

		Query q = session.createQuery("from Language where active=true");

		return q.list();
	}
}
