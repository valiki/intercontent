package net.sunnycore.intercontent.dataaccess;

import net.sunnycore.intercontent.domain.Content;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/dataaccess-test-context.xml" })
@TransactionConfiguration(defaultRollback = true)
public class ContentDaoTest extends AbstractDaoTest<AbstractDao<Content>,Content> {

    @Autowired
    private ContentDao contentDao;

    @Override
    public AbstractDao<Content> getDao() {
        return contentDao;
    }

}
