package vn.com.viettel.vds.vm2.pochibernateenvers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;
import vn.com.viettel.vds.vm2.pochibernateenvers.entity.Category;

@Repository
public interface CategoryRepository extends RevisionRepository<Category, Long, Long>, JpaRepository<Category, Long> {

}
