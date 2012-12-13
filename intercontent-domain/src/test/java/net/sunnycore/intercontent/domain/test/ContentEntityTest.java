package net.sunnycore.intercontent.domain.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import net.sunnycore.intercontent.domain.Content;
import net.sunnycore.intercontent.domain.ContentData;
import net.sunnycore.intercontent.domain.ContentText;
import net.sunnycore.intercontent.domain.ContentTranslation;
import net.sunnycore.intercontent.util.test.PersistenceTestBase;

import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domain-test-context.xml" })
@TransactionConfiguration(defaultRollback = true)
public class ContentEntityTest extends PersistenceTestBase {

    private static final String CONTENT_TEXT_RU = "Чутка текста";
    private static final String CONTENT_META_RU = "Мета";
    private static final String CONTENT_BRIEF_RU = "Бриф";
    private static final String CONTENT_TEXT_DEFAULT = "Some content text";
    private static final String CONTENT_META_DEFAULT = "Meta";
    private static final String CONTENT_BRIEF_DEFAULT = "Brief";
    private static final String RU_LANG_KEY = "RU";
    
    /**
     * tests Content object for dealing with translations
     */
    @Test
    public void testContentTranslations() {
        final Content content = createAndPersistContentWithTranslation();
        wrapInSeparateSessionAndTransaction(new Command() {

            @Override
            protected void execute(Session session) {
                Content secondContent = (Content) session.get(Content.class, content.getId());
                assertNotNull(secondContent);
                final ContentData sameTranslation = secondContent.getTranslation(RU_LANG_KEY);
                assertNotNull(sameTranslation);
                assertNotSame("The translation was not loaded", sameTranslation, secondContent);
                session.delete(secondContent);
            }
        });
    }

    /**
     * creates and saves with hibernate Content with it's translation
     * 
     * @return created Content
     */
    private Content createAndPersistContentWithTranslation() {
        final Content content = buildContent(CONTENT_BRIEF_DEFAULT, CONTENT_META_DEFAULT, CONTENT_TEXT_DEFAULT);
        final ContentTranslation contentTranslation = buildContentTranslation(CONTENT_BRIEF_RU, CONTENT_META_RU,
                CONTENT_TEXT_RU, RU_LANG_KEY);
        content.addTranslation(contentTranslation);
        wrapInSeparateSessionAndTransaction(new Command() {

            @Override
            protected void execute(Session session) {
                session.save(content);
            }
        });
        return content;
    }

    /**
     * builds Content object
     * 
     * @param brief
     * @param meta
     * @param text
     * @return
     */
    private Content buildContent(String brief, String meta, String text) {
        Content content = new Content();
        initContentDataFields(brief, meta, text, content);
        return content;
    }

    /**
     * build content translation object
     * 
     * @param brief
     * @param meta
     * @param text
     * @param lang
     * @return
     */
    private ContentTranslation buildContentTranslation(String brief, String meta, String text, String lang) {
        ContentTranslation contentTranslation = new ContentTranslation();
        initContentDataFields(brief, meta, text, contentTranslation);
        contentTranslation.setLang(lang);
        return contentTranslation;
    }

    /**
     * inits the fields specific for contentData entity
     * 
     * @param brief
     * @param meta
     * @param text
     * @param content
     */
    private void initContentDataFields(String brief, String meta, String text, ContentData content) {
        content.setBrief(brief);
        content.setMeta(meta);
        ContentText cText = new ContentText();
        cText.setText(text);
        content.setText(cText);
    }
}
