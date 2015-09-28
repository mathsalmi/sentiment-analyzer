package snet.model.services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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
			if(u == null) {
				throw new Exception();
			}

			String password = u.getPassword();
			boolean accountNonExpired = u.isActive();
			boolean accountNonLocked = u.isActive();
			boolean credentialsNonExpired = u.isActive();
			boolean enabled = u.isActive();
			Collection<GrantedAuthority> authorities = Collections.emptyList(); // vazio porque não utilizamos permissões

			return new org.springframework.security.core.userdetails.User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		} catch(Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("Usuário inexistente!");
		}
	}
}
