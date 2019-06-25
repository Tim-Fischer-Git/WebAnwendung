package com.webanwendung.Entitys;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User{
    @Id
    private String username;
    private String password;
    private String fullname;
		private boolean active;

	public boolean getActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
    }
    
	public String getFullname() {
		return this.fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

    
}