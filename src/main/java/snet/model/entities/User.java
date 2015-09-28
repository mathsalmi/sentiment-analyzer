package snet.model.entities;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails {

	private static final long serialVersionUID = 6494740783283500882L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
	@SequenceGenerator(name = "user_id_gen", sequenceName = "sq_user_id", allocationSize = 1)
	private int id;

	@Column
	private String username;

	@Column
	private String password;

	@Column
	private String name;

	@Column
	private boolean active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	@Transient
	public Collection<GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	@Transient
	public boolean isAccountNonExpired() {
		return isActive();
	}

	@Override
	@Transient
	public boolean isAccountNonLocked() {
		return isActive();
	}

	@Override
	@Transient
	public boolean isCredentialsNonExpired() {
		return isActive();
	}

	@Override
	@Transient
	public boolean isEnabled() {
		return isActive();
	}
}
