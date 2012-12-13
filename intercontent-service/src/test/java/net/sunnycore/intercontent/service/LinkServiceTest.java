package net.sunnycore.intercontent.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/services-test-context.xml" })
@TransactionConfiguration(defaultRollback = true)
public class LinkServiceTest {
    
    @Test
    public void test() {

    }
    
}
