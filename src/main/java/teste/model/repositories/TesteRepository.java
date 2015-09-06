package teste.model.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import teste.model.entities.Teste;

@Repository
@Transactional(readOnly = true)
public class TesteRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Teste> list() {
		Session session = sessionFactory.getCurrentSession();

		List<Teste> personList = session.createQuery("from Teste").list();

		return personList;
	}
}
