package vn.com.viettel.vds.vm2.pochibernateenvers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;
import vn.com.viettel.vds.vm2.pochibernateenvers.entity.Domain;

@Repository
public interface DomainRepository extends RevisionRepository<Domain, Long, Long>, JpaRepository<Domain, Long> {

}
