package net.sunnycore.intercontent.service;

import net.sunnycore.intercontent.dataaccess.CommentDao;
import net.sunnycore.intercontent.domain.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * the service for {@link Comment} entity manipulations
 * 
 * @author vshukaila
 * 
 */
@Service
public class CommentService extends AbstractService<Comment> {

    @Autowired
    private CommentDao commentDao;
    
    @Override
    protected CommentDao getDao() {
        return commentDao;
    }

}
