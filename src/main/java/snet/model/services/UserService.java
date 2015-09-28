package snet.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import snet.model.entities.User;
import snet.model.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	/**
	 * Retorna objeto de login do Spring Security
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			User u = userRepo.findByUsername(username);
			if (u == null) {
				throw new Exception();
			}

			return u;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("Usuário inexistente!");
		}
	}
}
