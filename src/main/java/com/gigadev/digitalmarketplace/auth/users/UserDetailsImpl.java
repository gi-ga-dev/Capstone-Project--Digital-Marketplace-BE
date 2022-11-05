package com.gigadev.digitalmarketplace.auth.users;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl  implements UserDetails{

	// JwtResponse jwtresp - prende i dati da restituire dopo il login
	private Long id;
	private String userName;
	@JsonIgnore
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private Double accountBalance;
	private Boolean isSubscribed;

	public static UserDetailsImpl build(User user) {		
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
				.collect(Collectors.toList());	
		return new UserDetailsImpl(
				user.getId(), 
				user.getUserName(), 
				user.getPassword(), 
				authorities, user.getAccountBalance(), user.getIsSubscribed());
	}
	
	// Getters - Setters 

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}


	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
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


}
