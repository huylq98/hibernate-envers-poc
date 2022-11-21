package vn.com.viettel.vds.vm2.pochibernateenvers.service;

import java.util.List;
import vn.com.viettel.vds.vm2.pochibernateenvers.dto.request.DomainRequestDto;
import vn.com.viettel.vds.vm2.pochibernateenvers.dto.response.HistoryResponseDto;
import vn.com.viettel.vds.vm2.pochibernateenvers.dto.response.DomainResponseDto;

public interface DomainService {

    List<DomainResponseDto> findAll();

    DomainResponseDto save(DomainRequestDto request);

    void delete(Long id);

    List<HistoryResponseDto> getHistory(Long domainId);
}
