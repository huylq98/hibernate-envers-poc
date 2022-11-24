package vn.com.viettel.vds.vm2.pochibernateenvers.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Table(name = "category")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Audited
@AuditTable("category_audit")
public class Category extends AuditTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "version")
    @Version
    private Long version;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain;

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return Objects.equals(((Category) obj).getId(), this.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
