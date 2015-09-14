package snet.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import snet.model.entities.Teste;
import snet.model.repositories.TesteRepository;

@Service
public class TesteService {
	@Autowired
	private TesteRepository indexDAO;

	public List<Teste> list() {
		return indexDAO.list();
	}
}
