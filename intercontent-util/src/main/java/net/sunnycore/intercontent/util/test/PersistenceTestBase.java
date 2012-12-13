package net.sunnycore.intercontent.util.test;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class PersistenceTestBase {
    
    private Logger logger = Logger.getLogger(PersistenceTestBase.class);
    
    @Autowired
    protected SessionFactory sessionFactory;
    
    protected static abstract class Command{
        protected abstract void execute(Session session);
    };
    
    public void wrapInSeparateSessionAndTransaction(Command command){
        Session session;
        try{
        	session = sessionFactory.getCurrentSession();
        }catch (HibernateException e) {
        	logger.debug("No session bound to current thread. Creating new one.");
        	session = sessionFactory.openSession();
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		}
        session.beginTransaction();
        try {
            command.execute(session);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            session.getTransaction().rollback();
            Assert.fail();
        }finally{
            TransactionSynchronizationManager.unbindResource(sessionFactory);
            session.close();
        }
    }
    
    public void wrapInSeparateSessionAndTransactionRollBack(Command command){
    	Session session;
        try{
        	session = sessionFactory.getCurrentSession();
        }catch (HibernateException e) {
        	logger.debug("No session bound to current thread. Creating new one.");
        	session = sessionFactory.openSession();
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		}
        session.beginTransaction();
        try {
            command.execute(session);
            session.flush();
            session.getTransaction().rollback();
            
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            session.getTransaction().rollback();
        }finally{
            TransactionSynchronizationManager.unbindResource(sessionFactory);
            session.close();
        }
    }
}
