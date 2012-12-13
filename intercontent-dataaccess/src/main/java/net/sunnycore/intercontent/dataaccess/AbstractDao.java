package net.sunnycore.intercontent.dataaccess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sunnycore.intercontent.domain.Persistent;
import net.sunnycore.intercontent.paging.PagingConfig;
import net.sunnycore.intercontent.paging.PagingConfig.OrderDirection;
import net.sunnycore.intercontent.paging.PagingResult;
import net.sunnycore.intercontent.util.GenericUtils;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * abstract class which is base for all hibernate daos
 * 
 * @author vshukaila
 *
 * @param <T> the hibernate entity
 */
@Repository
public abstract class AbstractDao<T extends Persistent> {

    // private Logger logger = Logger.getLogger(AbstractDao.class);

    /**
     * hibernate session factory
     */
    @Autowired
    private SessionFactory sessionFactory;

    private Class<T> persistentClass;

    public AbstractDao() {
        persistentClass = GenericUtils.getParameterClass(this.getClass());
    }

    /**
     * return the entity class that is currently managed by this dao
     * @return
     */
    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    /**
     * fetch entity by id from database
     * 
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public T fetchById(final long id) {

        final Session session = getCurrentSession();
        session.setCacheMode(CacheMode.NORMAL);
        T result;
        result = (T) session.get(this.getPersistentClass(), id);

        return result;
    } // end method fetchById

    public Session getCurrentSession() {
        Session session = this.sessionFactory.getCurrentSession();
        return session;
    }

    /**
     * fetch the list of entities from database by their ids
     * @param ids
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> fetchByIds(final Collection<Long> ids) {
        final Session session = this.getCurrentSession();
        final StringBuffer stringIds = new StringBuffer(StringUtils.collectionToCommaDelimitedString(ids));
        StringBuffer stringQuery = new StringBuffer("from ");
        stringQuery.append(this.getClassName()).append(" where id in (").append(stringIds).append(")");

        List<T> result;
        Query query = session.createQuery(stringQuery.toString());
        query.setCacheable(true);
        result = query.list();

        return result;
    } // end method fetchByIds

    /**
     * delete the entity from database
     * 
     * @param entity
     */
    public void delete(final T entity) {
        Session session = this.getCurrentSession();
        session.delete(entity);
    }

    /**
     * fetch all entities of T class from database
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<T> fetchAll() {
        final Session session = this.getCurrentSession();
        final List<T> result = new ArrayList<T>();
        Criteria criteria = session.createCriteria(this.getPersistentClass());
        criteria.setCacheable(true);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        result.addAll(criteria.list());
        return result;
    } // end method getAll

    /**
     * saves or updates the entity
     * @param entity
     */
    public void save(T entity) {
        final Session session = getCurrentSession();
        session.saveOrUpdate(entity);
    } // end method save

    /**
     * merges the persistent object with the entity and returns the result
     * @param entity
     * @return
     */
    @SuppressWarnings("unchecked")
    public T merge(T entity){
        final Session session = getCurrentSession();
        return (T) session.merge(entity);
    }
    
    /**
     * refreshes the entity using the persistent entity
     * 
     * @param entity
     */
    public void refresh(T entity){
        final Session session = getCurrentSession();
        session.refresh(entity);
    }
    
    /**
     * check if the <code>fieldName</code> of entity object in database 
     * does not have the <code>fieldValue</code>
     * @param fieldName
     * @param fieldValue
     * @return
     */
    public boolean checkValueNotExists(final String fieldName, final String fieldValue) {
        Session session = this.getCurrentSession();
        Criteria criteria = session.createCriteria(getPersistentClass());
        Long result;
        result = (Long) criteria.add(Restrictions.eq(fieldName, fieldValue)).setProjection(Projections.rowCount())
                .uniqueResult();

        return (result == 0);
    }

    /**
     * add order criteria to current search criteria
     * 
     * @param orderProperty
     * @param direction
     * @param hiberCriteria
     */
    protected void addOrder(String orderProperty, OrderDirection direction, Criteria hiberCriteria) {
        Order order;
        if (orderProperty != null && !"".equals(orderProperty.trim()) && direction != null) {
            switch (direction) {
            case ASC: {
                order = Order.asc(orderProperty);
                hiberCriteria.addOrder(order);
                break;
            }
            case DESC: {
                order = Order.desc(orderProperty);
                hiberCriteria.addOrder(order);
                break;
            }
            case NONE: {
                // then do nothing
            }
            }
        }
    }

    /**
     * adds paging to the current criteria
     * 
     * @param firstResult
     * @param maxResult
     * @param hiberCriteria
     */
    protected void addPaging(int firstResult, int maxResult, Criteria hiberCriteria) {
        hiberCriteria.setMaxResults(maxResult);
        hiberCriteria.setFirstResult(firstResult);
    }

    /**
     * adds additional search criteria to the paging of this dao
     * Needs to be overriden in subclass to work
     * @param config
     * @param criteria
     */
    protected void addSearchCriteria(PagingConfig<T> config, Criteria criteria) {
        // nothing to search by default. To add search override this method in
        // subclasses
    }

    protected StringBuffer getClassName() {
        StringBuffer result = new StringBuffer();
        result.append(this.getPersistentClass().toString());
        result = result.replace(0, 6, "");

        return result;
    }

    /**
     * counts the number of rows returned by the criteria
     * 
     * @param criteria
     * @return
     */
    protected long count(Criteria criteria) {
        Projection projection = Projections.rowCount();
        criteria.setProjection(projection);
        Long numberOfResult = (Long) criteria.uniqueResult();
        return numberOfResult;
    }

    /**
     * page over the result set
     * 
     * @param config
     * @return
     */
    @SuppressWarnings("unchecked")
    public PagingResult<T> page(PagingConfig<T> config) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(getPersistentClass());
        criteria.setCacheable(true);
        Criteria countCritera = session.createCriteria(getPersistentClass());
        countCritera.setCacheable(true);
        addPaging(config.getFirstResult(), config.getMaxResult(), criteria);
        addOrder(config.getOrderProperty(), config.getOrderDirection(), criteria);
        addSearchCriteria(config, criteria);
        addSearchCriteria(config, countCritera);
        PagingResult<T> result = config.getPageResult();
        result.setResult(criteria.list());
        long count = count(countCritera);
        result.setFullSize(count);
        return result;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
} // end class AbstractDao
