package net.sunnycore.intercontent.domain.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	CommentEntityTest.class, 
	ContentEntityTest.class, 
	LinkEntityTest.class,
	PrincipalUserEntityTest.class,
	UserEntityTest.class})
public class EntitiesTestSuite {

}
