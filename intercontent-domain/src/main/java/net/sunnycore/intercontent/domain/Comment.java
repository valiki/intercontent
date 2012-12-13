package net.sunnycore.intercontent.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "COMMENTS")
public class Comment extends CommentData {

    /**
     * 
     */
    private static final long serialVersionUID = -8235011060945753389L;

    /**
     * the parent comment to which current comment should be an answer
     */
    private Comment parent;
    /**
     * the list of child comments - answers to current comment
     */
    private List<Comment> comments = new ArrayList<Comment>();
    /**
     * the list of translations to the current comment
     */
    private Map<String, CommentTranslation> translations = new HashMap<String, CommentTranslation>();

    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_ID")
    public Content getContent() {
        return content;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    public Comment getParent() {
        return parent;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    public List<Comment> getComments() {
        return comments;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "origin", cascade = CascadeType.ALL)
    @MapKey(name = "lang")
    protected Map<String, CommentTranslation> getTranslations() {
        return translations;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    protected void setTranslations(Map<String, CommentTranslation> translations) {
        this.translations = translations;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    /**
     * return appropriate comment data for the entered language string if not
     * found any then default is returned
     * 
     * @param lang
     * @return
     */
    @Transient
    public CommentData getTranslation(String lang) {
        if (!StringUtils.isEmpty(lang)) {
            Map<String, CommentTranslation> _translations = getTranslations();
            CommentTranslation commentTranslation = _translations.get(lang);
            if (commentTranslation != null) {
                return commentTranslation;
            } else {
                return this;
            }
        } else {
            return this;
        }
    }

    /**
     * adds translation to the comment
     * 
     * @param translation
     */
    @Transient
    public void addTranslation(CommentTranslation translation) {
        Map<String, CommentTranslation> _translations = getTranslations();
        final String lang = translation.getLang();
        if(StringUtils.isEmpty(lang)){
            throw new IllegalArgumentException("The language should be set for translation");
        }
        _translations.put(lang, translation);
        translation.setOrigin(this);
    }
}
