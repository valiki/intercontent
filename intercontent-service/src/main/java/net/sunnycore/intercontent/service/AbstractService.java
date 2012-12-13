package net.sunnycore.intercontent.service;

import java.util.Collection;
import java.util.List;

import net.sunnycore.intercontent.dataaccess.AbstractDao;
import net.sunnycore.intercontent.domain.Persistent;
import net.sunnycore.intercontent.paging.PagingConfig;
import net.sunnycore.intercontent.paging.PagingResult;

import org.springframework.transaction.annotation.Transactional;
/**
 * the abstract class that is basis for all entity manipulation services
 * 
 * @author vshukaila
 *
 * @param <S> the entity Class
 * @param <T> the entity Dao class
 */
@Transactional
public abstract class AbstractService<S extends Persistent> {
    
    protected abstract AbstractDao<S> getDao();
    
    public S fetchById(final Long id){
        return getDao().fetchById(id);
    }
    
    public List<S> fetchByIds(final Collection<Long> ids) {
        return getDao().fetchByIds(ids);
    }
    
    @Transactional(readOnly=false)
    public void delete(final S entity) {
        getDao().delete(entity);
    }
    
    @Transactional(readOnly=false)
    public void save(final S entity){
        getDao().save(entity);
    }
    
    @Transactional(readOnly=false)
    public void refresh(final S entity){
        getDao().refresh(entity);
    }
    
    @Transactional(readOnly=false)
    public S merge(final S entity){
        return getDao().merge(entity);
    }
    
    public List<S> page(PagingConfig<S> config){
        final PagingResult<S> page = getDao().page(config);
        return page.getResult();
    }
}
