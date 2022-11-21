package vn.com.viettel.vds.vm2.pochibernateenvers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "vn.com.viettel.vds.vm2.pochibernateenvers.repository")
@EnableTransactionManagement
public class PocHibernateEnversApplication {

    public static void main(String[] args) {
        SpringApplication.run(PocHibernateEnversApplication.class, args);
    }

}
