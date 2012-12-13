package net.sunnycore.intercontent.service;

import net.sunnycore.intercontent.dataaccess.PrincipalUserDao;
import net.sunnycore.intercontent.domain.PrincipalUser;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * The service used for security purposes
 * 
 * @author Valiantsin Shukaila
 *
 */
@Service(SecurityService.ID)
public class SecurityService implements UserDetailsService{
	
	public static final String ID = "securityService";
	
	@Autowired
	private PrincipalUserDao principalUserDao;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		try {
			PrincipalUser user = principalUserDao.loadByUserName(username);
			if(user==null){
				throw new UsernameNotFoundException("Invalid Credentials");
			}
			return user;
		} catch (HibernateException e) {
			throw new UsernameNotFoundException("Invalid Credentials", e);
		}
	}
}
