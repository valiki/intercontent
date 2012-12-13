package net.sunnycore.intercontent.util.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@SuppressWarnings("deprecation")
public class HibernateUtil {

private static final SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(HibernateUtil.class);
    static {
        try {
            final Configuration configure = new Configuration().configure();
            sessionFactory = configure.buildSessionFactory();
        } catch (Throwable ex) {
        	logger.error("oops", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession()
            throws HibernateException {
        return sessionFactory.openSession();
    }
}
