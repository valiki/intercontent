package net.sunnycore.intercontent.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * the abstract data entity that contains only data fields that can have
 * translated variants
 * 
 * @author Val
 * 
 */
@MappedSuperclass
public abstract class LinkData extends Persistent {

    /**
     * 
     */
    private static final long serialVersionUID = -8696900643472064168L;

    /**
     * the url if specified
     */
    private String url;
    /**
     * the title of the page under this link if not external url
     */
    private String title;
    /**
     * the name of the link
     */
    private String name;

    @Column(name = "URL", length = 255)
    public String getUrl() {
        return url;
    }

    @Column(name = "TITLE", length = 150)
    public String getTitle() {
        return title;
    }

    @Column(name = "NAME", length = 100)
    public String getName() {
        return name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

}
