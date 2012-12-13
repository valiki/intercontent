package net.sunnycore.intercontent.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

/**
 * the entity that represents page content
 * 
 * @author Val
 * 
 */
@Entity
@Table(name = "CONTENTS")
public class Content extends ContentData{

    /**
     * 
     */
    private static final long serialVersionUID = -7123291545406976440L;

    private Map<String, ContentTranslation> translations = new HashMap<String, ContentTranslation>();

    private List<Comment> comments;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "origin")
    @MapKey(name = "lang")
    protected Map<String, ContentTranslation> getTranslations() {
        return translations;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "content", cascade = CascadeType.ALL)
    public List<Comment> getComments() {
        return comments;
    }

    protected void setTranslations(Map<String, ContentTranslation> translations) {
        this.translations = translations;
    }

    /**
     * returns the appropriate content translation for the entered language code
     * if is empty - returns default value
     * 
     * @param lang
     * @return
     */
    @Transient
    public ContentData getTranslation(String lang) {
        if (!StringUtils.isEmpty(lang)) {
            final Map<String, ContentTranslation> _translations = getTranslations();
            final ContentTranslation contentTranslation = _translations.get(lang);
            if (contentTranslation != null) {
                return contentTranslation;
            } else {
                return this;
            }
        } else {
            return this;
        }
    }

    /**
     * ads translation for the content
     * 
     * @param translation
     */
    @Transient
    public void addTranslation(ContentTranslation translation) {
        final Map<String, ContentTranslation> _translations = getTranslations();
        final String lang = translation.getLang();
        if(StringUtils.isEmpty(lang)){
            throw new IllegalArgumentException("The language should be set for translation");
        }
        _translations.put(lang, translation);
        translation.setOrigin(this);
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
