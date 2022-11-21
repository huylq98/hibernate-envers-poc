package vn.com.viettel.vds.vm2.pochibernateenvers.mapper;

import java.util.Collection;

public interface BaseMapper<Q, E, S> {

    E toEntity(Q request);

    S toDto(E entity);

    Collection<S> toDtos(Collection<E> entities);
}
