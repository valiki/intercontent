package net.sunnycore.intercontent.domain.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import net.sunnycore.intercontent.domain.PrincipalUser;
import net.sunnycore.intercontent.util.test.PersistenceTestBase;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { Configuration.CONFIG_CONTEXT_LOCATION })
@TransactionConfiguration(defaultRollback = true)
public class PrincipalUserEntityTest extends PersistenceTestBase{

	private static final String TEST_PASSWORD = "testPassword";
	private static final String TEST_USERNAME = "valiki@username.com";

	@Test
	public void testPrincipalUser(){
		final PrincipalUser principalUser = createAndSavePrincipalUser();
		wrapInSeparateSessionAndTransaction(new Command() {
			
			@Override
			protected void execute(Session session) {
				PrincipalUser user = (PrincipalUser) session.get(PrincipalUser.class, principalUser.getId());
				assertNotNull(user);
				assertEquals(user, principalUser);
				assertEquals(user.getUsername(), TEST_USERNAME);
				assertEquals(user.getPassword(), TEST_PASSWORD);
			}
		});
	}

	private PrincipalUser createAndSavePrincipalUser() {
		final PrincipalUser principalUser = new PrincipalUser();
		principalUser.setUsername(TEST_USERNAME);
		principalUser.setPassword(TEST_PASSWORD);
		wrapInSeparateSessionAndTransaction(new Command() {
			
			@Override
			protected void execute(Session session) {
				session.save(principalUser);
			}
		});
		return principalUser;
	}
	
}
