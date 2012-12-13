package net.sunnycore.intercontent.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
/**
 * Main user principal for the security system that can be switched to another one
 * 
 * @author Valiantsin Shukaila
 *
 */
@Entity
@Table(name="PRINCIPAL_USER")
public class PrincipalUser extends Persistent implements Principal{
	/**
	 * the username(email)
	 */
	private String username;
	/**
	 * the password md5 hash with salt
	 */
	private String password;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	/**
	 * the date and time when the prncipal was created.
	 * It is initialized on object creation and than stored in db
	 */
	private Date createdDate = new Date();
	private Set<GrantedAuthority> authorities;
	/**
	 * 
	 */
	private static final long serialVersionUID = -5229686588357978589L;

	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> auth = new HashSet<GrantedAuthority>();
		auth.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return authorities;
	}

	@Column(name="PASSWORD",nullable=false)
	public String getPassword() {
		return password;
	}

	@Column(name="USER_NAME",nullable=false,unique=true)
	public String getUsername() {
		return username;
	}

	@Column(name="ACCOUNT_NON_EXPIRED",columnDefinition="BIT NOT NULL DEFAULT 1")
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Column(name="ACCOUNT_NON_LOCKED",columnDefinition="BIT NOT NULL DEFAULT 1")
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Column(name="CREDENTIALS_NON_EXPIRED",columnDefinition="BIT NOT NULL DEFAULT 1")
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Column(name="ENABLED")
	public boolean isEnabled() {
		return enabled;
	}
	
	@Column(name="CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedDate() {
		return createdDate;
	}

	@Transient
	@Override
	public String generateSalt() {
		return createdDate.getTime()+12345L+"haha";
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
