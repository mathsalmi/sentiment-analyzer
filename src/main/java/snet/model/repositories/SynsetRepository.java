package snet.model.repositories;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import snet.dto.SynsetValueDTO;
import snet.model.entities.Language;
import snet.model.entities.Synset;

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

		Query q = session.createQuery("select s from Synset s inner join fetch s.terms where s.id=:id");
		q.setInteger("id", id);

		return (Synset) q.uniqueResult();
	}

	public List<Synset> getByLangId(String langId) {
		Session session = sessionFactory.getCurrentSession();

		Query q = session.createQuery("select s from Synset s where s.language.id=:langId");
		q.setMaxResults(15); // TODO: max results per page
		q.setString("langId", langId);

		return q.list();
	}

	@Transactional(readOnly=false)
	public void save(Synset synset) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(synset);
	}
}
