package snet.model.repositories;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class SynsetTermRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional(readOnly=false)
	public void deleteBySynsetId(int id) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createSQLQuery("delete from synset_term where synset_id=:id");
		q.setInteger("id", id);
		q.executeUpdate();
	}
}
