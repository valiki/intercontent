package net.sunnycore.intercontent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sunnycore.intercontent.dataaccess.LinkDao;
import net.sunnycore.intercontent.domain.Link;

/**
 * the service for {@link Link} entity manipulations
 * 
 * @author vshukaila
 * 
 */
@Service
public class LinkService extends AbstractService<Link> {

    @Autowired
    private LinkDao linkDao;

    @Override
    protected LinkDao getDao() {
        return linkDao;
    }

}
