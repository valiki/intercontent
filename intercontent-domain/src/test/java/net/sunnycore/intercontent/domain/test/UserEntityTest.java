package net.sunnycore.intercontent.domain.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import net.sunnycore.intercontent.domain.User;
import net.sunnycore.intercontent.util.test.PersistenceTestBase;

import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { Configuration.CONFIG_CONTEXT_LOCATION})
@TransactionConfiguration(defaultRollback = true)
public class UserEntityTest extends PersistenceTestBase {

	private static final String TEST_USERNAME = "test@username.com";

	@Test
	public void testUser(){
		final User user = createAndSaveUser();
		wrapInSeparateSessionAndTransaction(new Command() {
			
			@Override
			protected void execute(Session session) {
				User sameUser = (User) session.get(User.class, user.getId());
				assertNotNull(sameUser);
				assertEquals(user, sameUser);
				assertEquals(user.getEmailUsername(), sameUser.getEmailUsername());
			}
		});
	}
	
	private User createAndSaveUser(){
		final User user = new User();
		user.setEmailUsername(TEST_USERNAME);
		wrapInSeparateSessionAndTransaction(new Command() {
			
			@Override
			protected void execute(Session session) {
				session.save(user);
			}
		});
		return user;
	}
	
}
