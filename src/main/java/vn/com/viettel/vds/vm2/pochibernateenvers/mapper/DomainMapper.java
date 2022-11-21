package vn.com.viettel.vds.vm2.pochibernateenvers.mapper;

import org.mapstruct.Mapper;
import vn.com.viettel.vds.vm2.pochibernateenvers.dto.request.DomainRequestDto;
import vn.com.viettel.vds.vm2.pochibernateenvers.dto.response.DomainResponseDto;
import vn.com.viettel.vds.vm2.pochibernateenvers.entity.Domain;

@Mapper(componentModel = "spring")
public interface DomainMapper extends BaseMapper<DomainRequestDto, Domain, DomainResponseDto> {

}
