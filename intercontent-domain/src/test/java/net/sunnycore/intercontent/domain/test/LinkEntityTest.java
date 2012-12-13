package net.sunnycore.intercontent.domain.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import net.sunnycore.intercontent.domain.Link;
import net.sunnycore.intercontent.domain.LinkData;
import net.sunnycore.intercontent.domain.LinkTranslation;
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
public class LinkEntityTest extends PersistenceTestBase {

    private static final String LINK_NAME_BY = "Имя";
    private static final String LINK_TITLE_BY = "Название";
    private static final String LINK_TITLE_DEFAULT = "Link title";
    private static final String LINK_NAME_DEFAULT = "link name";
    private static final String SOME_URL_COM = "http://google.com";
    private static final String SOME_URL_BY = "http://google.by";
    private static final String RU_LANG_KEY = "RU";
	
    /**
     * tests Link object for dealing with translations
     */
    @Test
    public void testLinkTranslations() {
        final Link link = createAndPersistLinkWithTranslation();
        wrapInSeparateSessionAndTransaction(new Command() {

            @Override
            protected void execute(Session session) {
                Link secondLink = (Link) session.get(Link.class, link.getId());
                assertNotNull(secondLink);
                LinkData linkForLang = secondLink.getTranslation(RU_LANG_KEY);
                assertNotNull(linkForLang);
                assertNotSame("The translation was not loaded", linkForLang, secondLink);
                session.delete(secondLink);
            }
        });
    }

    /**
     * creates and saves with hibernate Link with it's translation
     * 
     * @return created Link
     */
    private Link createAndPersistLinkWithTranslation() {
        final Link link = buildLink(SOME_URL_COM, LINK_NAME_DEFAULT, LINK_TITLE_DEFAULT);
        final LinkTranslation translation = buildLinkTranslation(SOME_URL_BY, LINK_NAME_BY, LINK_TITLE_BY, RU_LANG_KEY);
        link.addTranslation(translation);
        wrapInSeparateSessionAndTransaction(new Command() {

            @Override
            public void execute(Session session) {
                session.save(link);
            }
        });
        return link;
    }

    /**
     * builds link
     * 
     * @param url
     * @param name
     * @param title
     * @return
     */
    private Link buildLink(String url, String name, String title) {
        Link link = new Link();
        initLinkDataFields(url, name, title, link);
        return link;
    }

    /**
     * builds link translation
     * 
     * @param url
     * @param name
     * @param title
     * @param lang
     * @return
     */
    private LinkTranslation buildLinkTranslation(String url, String name, String title, String lang) {
        LinkTranslation translation = new LinkTranslation();
        initLinkDataFields(url, name, title, translation);
        translation.setLang(lang);
        return translation;
    }

    /**
     * inits the fields specific for the LinkData entity
     * 
     * @param url
     * @param name
     * @param title
     * @param link
     */
    private void initLinkDataFields(String url, String name, String title, LinkData link) {
        link.setUrl(url);
        link.setName(name);
        link.setTitle(title);
    }
    
}
