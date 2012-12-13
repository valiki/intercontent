package net.sunnycore.intercontent.domain.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import net.sunnycore.intercontent.domain.Comment;
import net.sunnycore.intercontent.domain.CommentData;
import net.sunnycore.intercontent.domain.CommentTranslation;
import net.sunnycore.intercontent.util.test.PersistenceTestBase;

import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { Configuration.CONFIG_CONTEXT_LOCATION })
@TransactionConfiguration(defaultRollback = true)
public class CommentEntityTest extends PersistenceTestBase {

	private static final String COMMENT_AUTHOR_RU = "Тестовый автор";
    private static final String COMMENT_RU = "Тестовый Комментарий";
    private static final Date COMMENT_DATE_DEFAULT = new Date();
    private static final String COMMENT_AUTHOR_DEFAULT = "Test Author";
    private static final String COMMENT_DEFAULT = "Test Comment";
    private static final String RU_LANG_KEY = "RU";
    
    /**
     * test Comment object for dealing with the translations
     */
    @Test
    public void testCommentTranslation() {
        final Comment comment = createAndPersistCommentWithTranslation();
        wrapInSeparateSessionAndTransaction(new Command() {

            @Override
            protected void execute(Session session) {
                final Comment sameComment = (Comment) session.get(Comment.class, comment.getId());
                assertNotNull(sameComment);
                final CommentData ruComment = sameComment.getTranslation(RU_LANG_KEY);
                assertNotNull(ruComment);
                assertNotSame("The translation was not loaded", ruComment, sameComment);
                session.delete(sameComment);
            }
        });
    }
    /**
     * test the comment self contained mapping that defines tree of comments
     */
    @Test
    public void testCommentTree(){
        final Comment comment1 = buildDefaultComment();
        final Comment comment2 = buildDefaultComment();
        final Comment comment3 = buildDefaultComment();
        //comment1 contains comment 2 and 3
        comment2.setParent(comment1);
        comment3.setParent(comment1);
        final Comment comment4 = buildDefaultComment();
        //comment2 contains comment4
        comment4.setParent(comment2);
        final Comment comment5 = buildDefaultComment();
        //comment3 contains comment 5
        comment5.setParent(comment3);
        wrapInSeparateSessionAndTransaction(new Command() {
            
            @Override
            protected void execute(Session session) {
                session.save(comment1);
                session.save(comment2);
                session.save(comment3);
                session.save(comment4);
                session.save(comment5);
            }
        });
        wrapInSeparateSessionAndTransaction(new Command() {
            
            @Override
            protected void execute(Session session) {
                Comment _comment1 = (Comment) session.get(Comment.class, comment1.getId());
                Comment _comment2 = (Comment) session.get(Comment.class, comment2.getId());
                Comment _comment3 = (Comment) session.get(Comment.class, comment3.getId());
                Comment _comment4 = (Comment) session.get(Comment.class, comment4.getId());
                Comment _comment5 = (Comment) session.get(Comment.class, comment5.getId());
                assertNotNull(_comment1);
                assertNotNull(_comment2);
                assertNotNull(_comment3);
                assertNotNull(_comment4);
                assertNotNull(_comment5);
                final List<Comment> _comments1 = _comment1.getComments();
                assertNotNull(_comments1);
                assertTrue(_comments1.contains(_comment2));
                assertTrue(_comments1.contains(_comment3));
                final List<Comment> _comments2 = _comment2.getComments();
                assertTrue(_comments2.contains(_comment4));
                final List<Comment> _comments3 = _comment3.getComments();
                assertTrue(_comments3.contains(_comment5));
                session.delete(_comment5);
                session.delete(_comment4);
                session.delete(_comment3);
                session.delete(_comment2);
                session.delete(_comment1);
            }
        });
    }

    /**
     * creates and saves with hibernate comment with it's translation
     * 
     * @return created Comment
     */
    private Comment createAndPersistCommentWithTranslation() {
        final Comment comment = buildDefaultComment();
        final CommentTranslation commentTranslation = buildCommentTranslation(COMMENT_RU, COMMENT_AUTHOR_RU,
                COMMENT_DATE_DEFAULT, RU_LANG_KEY);
        comment.addTranslation(commentTranslation);
        wrapInSeparateSessionAndTransaction(new Command() {

            @Override
            protected void execute(Session session) {
                session.save(comment);
            }
        });
        return comment;
    }


    /**
     * builds comment with default fields
     * @return
     */
    private Comment buildDefaultComment() {
        return buildComment(COMMENT_DEFAULT, COMMENT_AUTHOR_DEFAULT, COMMENT_DATE_DEFAULT);
    }
    
    /**
     * build comment translation object
     * 
     * @param comment
     * @param author
     * @param date
     * @param lang
     * @return
     */
    private CommentTranslation buildCommentTranslation(String comment, String author, Date date, String lang) {
        CommentTranslation commentTranslation = new CommentTranslation();
        initCommentDataFields(comment, author, date, commentTranslation);
        commentTranslation.setLang(lang);
        return commentTranslation;
    }

    /**
     * builds comment
     * 
     * @param comment
     * @param author
     * @param date
     * @return
     */
    private Comment buildComment(String comment, String author, Date date) {
        Comment _comment = new Comment();
        initCommentDataFields(comment, author, date, _comment);
        return _comment;
    }

    /**
     * inits the fields specific for the comment data
     * 
     * @param comment
     * @param author
     * @param date
     * @param _comment
     */
    private void initCommentDataFields(String comment, String author, Date date, CommentData _comment) {
        _comment.setComment(comment);
        _comment.setAuthorName(author);
        _comment.setDate(date);
    }
}
