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

@Repository
@Transactional(readOnly = true)
public class SynsetRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<SynsetValueDTO> listTokensWithValue(String[] terms, Language lang) {
		Session session = sessionFactory.getCurrentSession();

		String sql = "" +
				";with d as ( " +
				"	select " +
				"		term, " +
				"		sense_number, " +
				"		pos_speech, " +
				"		(positive_score - negative_score) val " +
				"	from synset_term st " +
				"	inner join synset s on st.synset_id=s.id " +
				"	where term in (:terms) and language_id=:langId" +
				")" +
				", o as ( " +
				"	select " +
				"		term " +
				"		,pos_speech " +
				"		,sum(val / cast(sense_number as float)) score " +
				"		,sum(1 / cast(sense_number as float)) summ " +
				"	from d " +
				"	group by term, pos_speech " +
				")" +
				"select " +
				"	term " +
				"	, pos_speech \"type\" " +
				"	, (score / cast(summ as float)) \"value\" " +
				"from o " +
				"order by 1, 2 ";

		Query q = session.createSQLQuery(sql);
		q.setParameterList("terms", terms);
		q.setString("langId", lang.getId());
		q.setResultTransformer(Transformers.aliasToBean(SynsetValueDTO.class));

		return q.list();
	}
}
