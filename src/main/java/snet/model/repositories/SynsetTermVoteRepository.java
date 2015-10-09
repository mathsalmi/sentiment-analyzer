package snet.model.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import snet.model.entities.SynsetTermVote;

@Repository
@Transactional(readOnly = true)
public class SynsetTermVoteRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional(readOnly=false)
	public void save(SynsetTermVote vote) throws Exception {
		Session session = sessionFactory.getCurrentSession();

		vote = (SynsetTermVote) session.merge(vote);

		session.saveOrUpdate(vote);
	}
}
