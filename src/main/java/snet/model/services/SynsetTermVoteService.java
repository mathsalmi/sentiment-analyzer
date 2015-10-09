package snet.model.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snet.model.entities.SynsetTermVote;
import snet.model.repositories.SynsetTermVoteRepository;

@Service
public class SynsetTermVoteService {

	@Autowired
	private SynsetTermVoteRepository voteRepo;

	public boolean save(SynsetTermVote vote) {
		try {
			vote.setDate(new Date());

			voteRepo.save(vote);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
