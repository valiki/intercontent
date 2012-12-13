package net.sunnycore.intercontent.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

/**
 * the entity that represents a link to some content or external url
 * 
 * @author Val
 * 
 */
@Entity
@Table(name = "LINKS")
public class Link extends LinkData {

    /**
     * 
     */
    private static final long serialVersionUID = 4688572611304235459L;

    private Map<String, LinkTranslation> translations = new HashMap<String, LinkTranslation>();
    /**
     * the content of the page underlying the link
     */
    private Content content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_ID")
    public Content getContent() {
        return content;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "origin")
    @MapKey(name = "lang")
    protected Map<String, LinkTranslation> getTranslations() {
        return translations;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    protected void setTranslations(Map<String, LinkTranslation> translations) {
        this.translations = translations;
    }

    /**
     * return appropriate link data for the entered language string if not found
     * any then default is returned
     * 
     * @param lang
     * @return
     */
    @Transient
    public LinkData getTranslation(String lang) {
        if (!StringUtils.isEmpty(lang)) {
            Map<String, LinkTranslation> _translations = getTranslations();
            LinkTranslation linkTranslation = _translations.get(lang);
            if (linkTranslation != null) {
                return linkTranslation;
            } else {
                return this;
            }
        } else {
            return this;
        }
    }

    /**
     * adds translation to the link
     * 
     * @param translation
     */
    @Transient
    public void addTranslation(LinkTranslation translation) {
        Map<String, LinkTranslation> _translations = getTranslations();
        final String lang = translation.getLang();
        if(StringUtils.isEmpty(lang)){
            throw new IllegalArgumentException("The language should be set for translation");
        }
        _translations.put(lang, translation);
        translation.setOrigin(this);
    }
}
