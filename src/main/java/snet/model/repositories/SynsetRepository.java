package snet.model.repositories;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import snet.dto.SynsetValueDTO;
import snet.model.entities.Language;
import snet.model.entities.Synset;
import snet.model.entities.SynsetTerm;

@Repository
@Transactional(readOnly = true)
@SuppressWarnings("unchecked")
public class SynsetRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public List<SynsetValueDTO> listTokensWithValue(String[] terms, Language lang) {
		Session session = sessionFactory.getCurrentSession();

		String sql = "" +
				";with dados as ( " +
				"	select " +
				"		term, " +
				"		sense_number, " +
				"		pos_speech, " +
				"		(positive_score - negative_score) val " +
				"	from synset_term st " +
				"	inner join synset s on st.synset_id=s.id " +
				"	where term in (:terms) and language_id=:langId" +
				")" +
				", dados_somados as ( " +
				"	select " +
				"		term " +
				"		,pos_speech " +
				"		,sum(val / cast(sense_number as float)) score " +
				"		,sum(1 / cast(sense_number as float)) summ " +
				"	from dados " +
				"	group by term, pos_speech " +
				")" +
				"select " +
				"	term " +
				"	, pos_speech \"type\" " +
				"	, (score / cast(summ as float)) \"value\" " +
				"from dados_somados " +
				"order by 1, 2 ";

		Query q = session.createSQLQuery(sql);
		q.setParameterList("terms", terms);
		q.setString("langId", lang.getId());
		q.setResultTransformer(Transformers.aliasToBean(SynsetValueDTO.class));

		return q.list();
	}

	public Synset getById(int id) {
		Session session = sessionFactory.getCurrentSession();

		Query q = session.createQuery("select s from Synset s left join fetch s.terms where s.id=:id");
		q.setInteger("id", id);

		return (Synset) q.uniqueResult();
	}

	public List<Synset> getByLangId(String langId) {
		Session session = sessionFactory.getCurrentSession();

		Query q = session.createQuery("select s from Synset s where s.language.id=:langId");
		q.setString("langId", langId);

		return q.list();
	}

	public Page<Synset> getByLangId(String langId, Pageable pageable) {
		Session session = sessionFactory.getCurrentSession();

		// total
		Query q = session.createQuery("select count(1) from Synset s where s.language.id=:langId");
		q.setString("langId", langId);
		Long total = (Long) q.uniqueResult();

		// results
		q = session.createQuery("select s from Synset s where s.language.id=:langId order by id");
		q.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
		q.setMaxResults(pageable.getPageSize());
		q.setString("langId", langId);

		return new PageImpl<Synset>(q.list(), pageable, total);
	}

	@Transactional(readOnly=false)
	public void save(Synset synset) throws Exception {
		Session session = sessionFactory.getCurrentSession();

		synset = (Synset) session.merge(synset);

		session.saveOrUpdate(synset);
	}

	@Transactional(readOnly=false)
	public void delete(int id) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createSQLQuery("delete from synset where id=:id");
		q.setInteger("id", id);
		q.executeUpdate();
	}

	// TODO: rewrite the SQL in order to make this method faster
	public SynsetTerm getTermRandomlyByLang(Language lang) {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("select st from SynsetTerm st inner join fetch st.synset s where s.language.id=:langId order by rand()");
		q.setParameter("langId", lang.getId());
		q.setMaxResults(1);

		return (SynsetTerm) q.uniqueResult();
	}

	@Transactional(readOnly=false)
	public void refreshValues(int id, Language lang) {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createSQLQuery("select fn_update_synset_with_votes(:langId, :id)");
		q.setParameter("langId", lang.getId());
		q.setParameter("id", id);
		q.uniqueResult();
	}

	@Transactional(readOnly=false)
	public void refreshAllValues(Language lang) {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createSQLQuery("select fn_update_synset_with_votes(:langId)");
		q.setParameter("langId", lang.getId());
		q.uniqueResult();
	}
}
