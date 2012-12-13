package net.sunnycore.intercontent.domain;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
/**
 * main security principal interface
 * @author Valiantsin Shukaila
 *
 */
public interface Principal extends UserDetails{
	/**
	 * gets creation date of the Principal object
	 * @return
	 */
	Date getCreatedDate();
	/**
	 * generates Salt for md5 hashing of the password
	 * 
	 * @return
	 */
	String generateSalt();
}
