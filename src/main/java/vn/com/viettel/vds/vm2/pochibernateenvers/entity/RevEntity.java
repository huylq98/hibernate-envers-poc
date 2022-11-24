package vn.com.viettel.vds.vm2.pochibernateenvers.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@RevisionEntity
@Table(name = "rev_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RevEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    @Column(name = "rev")
    private Long id;

    @RevisionTimestamp
    @Column(name = "revtstmp")
    private Long timestamp;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.getId(), ((RevEntity) obj).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
