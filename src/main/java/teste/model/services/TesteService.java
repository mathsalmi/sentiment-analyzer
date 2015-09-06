package teste.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import teste.model.entities.Teste;
import teste.model.repositories.TesteRepository;

@Service
public class TesteService {
	@Autowired
	private TesteRepository indexDAO;

	public List<Teste> list() {
		return indexDAO.list();
	}
}
