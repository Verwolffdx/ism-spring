package com.edatwhite.smkd.service.user;

import com.edatwhite.smkd.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String fio;

	private String username;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	private List<Long> divisions;

	public UserDetailsImpl(Long id, String fio, String username, String password,
			Collection<? extends GrantedAuthority> authorities, List<Long> divisions) {
		this.id = id;
		this.fio = fio;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.divisions = divisions;
	}

	public static UserDetailsImpl build(Users user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		List<Long> divisions = user.getDivisions().stream()
				.map(division -> division.getDivision_id())
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getUser_id(),
				user.getFio(),
				user.getUsername(),
				user.getPassword(), 
				authorities,
				divisions);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getFio() {
		return fio;
	}

	public List<Long> getDivisions() {
		return divisions;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}
