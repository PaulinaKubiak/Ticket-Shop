package pl.lodz.p.it.spjava.TSS.model;

import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Version
    private int wersja;

    @NotNull
    @Column(name = "DATA_UTWORZENIA", nullable = false, updatable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataUtworzenia;

    @NotNull
    @Column(name = "DATA_MODYFIKACJI", nullable = true, updatable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dataModyfikacji;

    @PrePersist
    public void initCreationDate() {
        dataUtworzenia = new Date();
    }

    @PreUpdate
    public void initModificationDate() {
        dataModyfikacji = new Date();
    }

    public int getVersion() {
        return wersja;
    }

    public Date getCreationDate() {
        return dataUtworzenia;
    }

    public void setCreationDate(Date creationDate) {
        this.dataUtworzenia = creationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.dataModyfikacji = modificationDate;
    }

    public Date getModificationDate() {
        return dataModyfikacji;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractEntity other = (AbstractEntity) obj;
        if (this.wersja != other.wersja) {
            return false;
        }
        if (!Objects.equals(this.dataUtworzenia, other.dataUtworzenia)) {
            return false;
        }
        if (!Objects.equals(this.dataModyfikacji, other.dataModyfikacji)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AbstractEntity{" + "version=" + wersja + ", creationDate=" + dataUtworzenia + ", modificationDate=" + dataModyfikacji + '}';
    }
}
