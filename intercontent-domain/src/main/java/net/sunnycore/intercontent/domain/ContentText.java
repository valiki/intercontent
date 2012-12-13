package net.sunnycore.intercontent.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * the actual text of the content
 * 
 * @author Val
 * 
 */
@Entity
@Table(name = "CONTENT_TEXT")
public class ContentText extends Persistent {

    /**
     * 
     */
    private static final long serialVersionUID = -976072630984811836L;
    private String text;

    @Lob
    @Column(name = "TEXT")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
