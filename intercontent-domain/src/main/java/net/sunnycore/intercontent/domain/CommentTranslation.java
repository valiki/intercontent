package net.sunnycore.intercontent.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "COMMENT_TRANSLATIONS")
public class CommentTranslation extends CommentData {

    /**
     * 
     */
    private static final long serialVersionUID = 3864208181113946380L;

    private String lang;

    private Comment origin;

    @Column(name = "LANG")
    public String getLang() {
        return lang;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID")
    public Comment getOrigin() {
        return origin;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setOrigin(Comment origin) {
        this.origin = origin;
    }

}
