package net.sunnycore.intercontent.dataaccess;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import net.sunnycore.intercontent.domain.Comment;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/dataaccess-test-context.xml" })
@TransactionConfiguration(defaultRollback = true)
public class CommentDaoTest extends AbstractDaoTest<AbstractDao<Comment>,Comment>{

    @Autowired
    private CommentDao commentDao;
    
    @Override
    public AbstractDao<Comment> getDao() {
        return commentDao;
    }

}
