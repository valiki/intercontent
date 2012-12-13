package net.sunnycore.intercontent.service;

import net.sunnycore.intercontent.dataaccess.ContentDao;
import net.sunnycore.intercontent.domain.Content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * the service for {@link Content} entity manipulations
 * 
 * @author vshukaila
 * 
 */
@Service
public class ContentService extends AbstractService<Content> {

    @Autowired
    private ContentDao contentDao;
    
    @Override
    protected ContentDao getDao() {
        return contentDao;
    }

}
