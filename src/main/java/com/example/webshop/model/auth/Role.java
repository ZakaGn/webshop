package com.example.webshop.model.auth;

import java.util.HashSet;
import java.util.Set;

public enum Role{
	MANAGER("ROLE_MANAGER"),
	EMPLOYER("ROLE_EMPLOYER"),
	CLIENT("ROLE_CLIENT"),
	;

	private final String role;
	private final Set<Role> managedRoles = new HashSet<>();

	static{
		MANAGER.managedRoles.add(EMPLOYER);
		MANAGER.managedRoles.add(CLIENT);
		EMPLOYER.managedRoles.add(CLIENT);
	}

	Role(String role){
		this.role = role;
	}

	@Override
	public String toString(){
		return role;
	}

}
