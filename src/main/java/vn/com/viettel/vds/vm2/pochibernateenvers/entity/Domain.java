package vn.com.viettel.vds.vm2.pochibernateenvers.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import org.hibernate.envers.AuditMappedBy;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Table(name = "domain")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Audited
@AuditTable("domain_audit")
public class Domain extends AuditTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "version")
    @Version
    private Long version;

    @Column(name = "name")
    private String name;

    @Column(name = "category_amount")
    private Integer categoryAmount;

    @OneToMany(mappedBy = "domain", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @AuditMappedBy(mappedBy = "domain")
    private Set<Category> categories;

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public void setCategoryAmount(Integer categoryAmount) {
        if (categoryAmount != null) {
            this.categoryAmount = categoryAmount;
        }
    }

    public void addCategories(Collection<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return;
        }
        if (this.categories == null) {
            this.categories = new HashSet<>();
        }
        categories.forEach(category -> category.setDomain(this));
        this.categories.addAll(categories);
    }
}
