package net.sunnycore.intercontent.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * the translation of the link text fields to some language and locale
 * 
 * @author Val
 * 
 */
@Entity
@Table(name = "LINK_TRANSLATIONS")
public class LinkTranslation extends LinkData {
    /**
     * 
     */
    private static final long serialVersionUID = -5747805921203491078L;
    /**
     * the locale to which this translation corresponds
     */
    private String lang;

    private Link origin;

    @Column(name = "LANG")
    public String getLang() {
        return lang;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LINK_ID")
    public Link getOrigin() {
        return origin;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setOrigin(Link origin) {
        this.origin = origin;
    }

}
