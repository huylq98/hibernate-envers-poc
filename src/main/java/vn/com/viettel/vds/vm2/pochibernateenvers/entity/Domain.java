package vn.com.viettel.vds.vm2.pochibernateenvers.entity;

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

    @OneToMany(mappedBy = "domain", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Category> categories;
}
