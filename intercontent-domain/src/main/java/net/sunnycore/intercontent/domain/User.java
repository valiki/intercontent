package net.sunnycore.intercontent.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User extends Persistent{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6824287336268711565L;
	/**
	 * user name of the user which is his email for connection with the security principal
	 */
	private String emailUsername;

	@Column(name="EMAIL_USER_NAME",nullable=false,unique=true)
	public String getEmailUsername() {
		return emailUsername;
	}

	public void setEmailUsername(String emailUsername) {
		this.emailUsername = emailUsername;
	}

}
