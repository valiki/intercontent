package net.sunnycore.intercontent.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public class ContentData extends Persistent {

    /**
     * 
     */
    private static final long serialVersionUID = -7153660433286720200L;
    /**
     * the brief of the page content text
     */
    private String brief;
    /**
     * meta information of the page content
     */
    private String meta;

    /**
     * the text content
     */
    private ContentText text;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CONTENT_TEXT_ID")
    public ContentText getText() {
        return text;
    }

    @Column(name = "BRIEF", length = 500)
    public String getBrief() {
        return brief;
    }

    @Column(name = "META", length = 500)
    public String getMeta() {
        return meta;
    }

    public void setText(ContentText text) {
        this.text = text;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

}
