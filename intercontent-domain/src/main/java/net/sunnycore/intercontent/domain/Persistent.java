package net.sunnycore.intercontent.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

/**
 * base persistent entity that has ID
 * 
 * @author Val
 * 
 */
@MappedSuperclass
public abstract class Persistent implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4179177835189010413L;
    /**
     * the id of the objet
     */
    private Long id;
    /**
     * the version control field for concurrency control with optimistic lock
     */
    private Integer version;
    
    private String note;
    
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    @Column(name="NOTE",length=100)
    public String getNote() {
        return note;
    }
    
    @Version
    @Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Persistent other = (Persistent) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    public void setNote(String note) {
        this.note = note;
    }

}
