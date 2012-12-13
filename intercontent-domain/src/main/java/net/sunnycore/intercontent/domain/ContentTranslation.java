package net.sunnycore.intercontent.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * the translation of the content to some language
 * 
 * @author Val
 * 
 */
@Entity
@Table(name = "CONTENT_TRANSLATIONS")
public class ContentTranslation extends ContentData{

    /**
     * 
     */
    private static final long serialVersionUID = -956695491719514616L;

    private Content origin;

    /**
     * the locale to which this translation corresponds
     */
    private String lang;
    
    @Column(name = "LANG")
    public String getLang() {
        return lang;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_ID")
    public Content getOrigin() {
        return origin;
    }

    public void setOrigin(Content origin) {
        this.origin = origin;
    }
    
    public void setLang(String lang) {
        this.lang = lang;
    }
}
