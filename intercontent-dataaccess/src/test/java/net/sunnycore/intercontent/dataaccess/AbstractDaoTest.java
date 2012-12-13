package net.sunnycore.intercontent.dataaccess;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import net.sunnycore.intercontent.domain.Persistent;
import net.sunnycore.intercontent.util.GenericUtils;
import net.sunnycore.intercontent.util.test.PersistenceTestBase;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.Test;

public abstract class AbstractDaoTest<T extends AbstractDao<S>,S extends Persistent> extends PersistenceTestBase{
    
    Logger logger = Logger.getLogger(getClass());
    
    public abstract AbstractDao<S> getDao();
    
    @Test
    public void testSave() throws InstantiationException, IllegalAccessException{
        wrapInSeparateSessionAndTransactionRollBack(new Command() {
            
            @Override
            protected void execute(Session session) {
                AbstractDao<S> _dao = getDao();
                S instance = createNewInstance();
                assertNotNull(instance);
                _dao.save(instance);
                assertNotNull(instance.getId());
            }
        });
    }
    
    @Test
    public void testFetchAll(){
        wrapInSeparateSessionAndTransactionRollBack(new Command() {
            
            @Override
            protected void execute(Session session) {
                AbstractDao<S> _dao = getDao();
                S inst1 = createNewInstance();
                assertNotNull(inst1);
                S inst2 = createNewInstance();
                assertNotNull(inst2);
                S inst3 = createNewInstance();
                assertNotNull(inst3);
                session.save(inst1);
                session.save(inst2);
                session.save(inst3);
                List<S> all = _dao.fetchAll();
                assertNotNull(all);
                assertEquals(3, all.size());
            }
        });
    }
    
    @Test
    public void testFetchById(){
        wrapInSeparateSessionAndTransactionRollBack(new Command() {
            
            @Override
            protected void execute(Session session) {
                AbstractDao<S> _dao = getDao();
                S inst1 = createNewInstance();
                assertNotNull(inst1);
                session.save(inst1);
                S fetchById = _dao.fetchById(inst1.getId());
                assertNotNull(fetchById);
            }
        });
    }

    @Test
    public void testFetchByIds(){
        wrapInSeparateSessionAndTransactionRollBack(new Command() {
            
            @Override
            protected void execute(Session session) {
                S inst1 = createNewInstance();
                S inst2 = createNewInstance();
                S inst3 = createNewInstance();
                assertNotNull(inst1);
                assertNotNull(inst2);
                assertNotNull(inst3);
                session.save(inst1);
                session.save(inst2);
                session.save(inst3);
                AbstractDao<S> _dao = getDao();
                List<Long> ids = new ArrayList<Long>();
                ids.add(inst1.getId());
                ids.add(inst2.getId());
                ids.add(inst3.getId());
                List<S> instances = _dao.fetchByIds(ids);
                assertTrue(instances.contains(inst1));
                assertTrue(instances.contains(inst2));
                assertTrue(instances.contains(inst3));
            }
        });
    }
    
    @Test
    public void testCheckValueNotExists(){
        wrapInSeparateSessionAndTransactionRollBack(new Command() {
            
            @Override
            protected void execute(Session session) {
                S inst1 = createNewInstance();
                inst1.setNote("Note");
                S inst2 = createNewInstance();
                inst2.setNote("Note1");
                session.save(inst1);
                session.save(inst2);
                AbstractDao<S> _dao = getDao();
                assertTrue(_dao.checkValueNotExists("note", "Note2"));
                S inst3 = createNewInstance();
                inst3.setNote("Note2");
                session.save(inst3);
                assertFalse(_dao.checkValueNotExists("note", "Note2"));
            }
        });
    }
    
    /**
     * creates new instance and saves it
     * 
     * @param _dao
     * @param entityClass
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private S createNewInstance(){
        AbstractDao<S> _dao = getDao();
        Class<S> entityClass = GenericUtils.getParameterClass(_dao.getClass());
        try{
            S newInstance = entityClass.newInstance();
            return newInstance;
        } catch (InstantiationException e) {
            logger.error(e);
        } catch (IllegalAccessException e) {
            logger.error(e);
        }
        return null;
    }
    
}
