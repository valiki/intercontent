package net.sunnycore.intercontent.dataaccess;

import net.sunnycore.intercontent.domain.Link;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/dataaccess-test-context.xml"})
@TransactionConfiguration(defaultRollback = true)
public class LinkDaoTest extends AbstractDaoTest<AbstractDao<Link>,Link>{

    @Autowired
    private LinkDao linkDao;
    
    @Override
    public AbstractDao<Link> getDao() {
        return linkDao;
    }

}
