package net.sunnycore.intercontent.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * the data entity for the Comment
 * 
 * @author Val
 * 
 */
@MappedSuperclass
public abstract class CommentData extends Persistent {

    /**
     * 
     */
    private static final long serialVersionUID = 8570022177829228709L;
    /**
     * actual comment
     */
    private String comment;
    /**
     * the name of the person who wrote the comment
     */
    private String authorName;
    /**
     * the date when the comment was posted
     */
    private Date date;

    @Column(name = "COMMENT", length = 255)
    public String getComment() {
        return comment;
    }

    @Column(name = "AUTHOR_NAME", length = 100)
    public String getAuthorName() {
        return authorName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE")
    public Date getDate() {
        return date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
