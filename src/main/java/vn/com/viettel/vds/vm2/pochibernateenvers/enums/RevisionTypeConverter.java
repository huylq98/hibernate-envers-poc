package vn.com.viettel.vds.vm2.pochibernateenvers.enums;

import org.hibernate.envers.RevisionType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RevisionTypeConverter implements Converter<String, RevisionType> {

    @Override
    public RevisionType convert(String source) {
        return RevisionType.valueOf(source);
    }
}
