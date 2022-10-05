package com.gigadev.digitalmarketplace.auth.users;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.gigadev.digitalmarketplace.auth.roles.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@NotBlank
	@Size(max = 50)
	private String firstName;
	
	@NotBlank
	@Size(max = 50)
	private String lastName;
	
	@NotBlank
	@Size(max = 50)
	private String email;

	@NotBlank
	@Size(max = 50)
	private String userName;

	@NotBlank
	@Size(max = 120) // il token e' molto lungo
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();
	
//	@OneToMany
//	private Set<AbstractProduct> products = new HashSet<AbstractProduct>();
	
	public User(@NotBlank @Size(max = 50) String userName, @NotBlank @Size(max = 120) String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
	
	public void addRole(Role role) {
		roles.add(role);
	}

}
