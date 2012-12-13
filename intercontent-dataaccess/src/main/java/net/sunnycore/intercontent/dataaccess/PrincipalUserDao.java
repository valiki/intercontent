package net.sunnycore.intercontent.dataaccess;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import net.sunnycore.intercontent.domain.PrincipalUser;

@Repository
public class PrincipalUserDao extends AbstractDao<PrincipalUser> {
	
	/**
	 * loads PrincipalUser by it's username
	 * @param username
	 * @return
	 */
	public PrincipalUser loadByUserName(final String username) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(PrincipalUser.class).add(
				Restrictions.eq("username", username));
		PrincipalUser result = (PrincipalUser) criteria.uniqueResult();
		return result;
	}
}
