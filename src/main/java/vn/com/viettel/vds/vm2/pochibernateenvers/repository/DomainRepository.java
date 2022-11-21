package vn.com.viettel.vds.vm2.pochibernateenvers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.viettel.vds.vm2.pochibernateenvers.entity.Domain;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {

}
