package vn.com.viettel.vds.vm2.pochibernateenvers.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.criteria.JoinType;
import org.hibernate.SessionFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.com.viettel.vds.vm2.pochibernateenvers.dto.request.DomainRequestDto;
import vn.com.viettel.vds.vm2.pochibernateenvers.dto.response.DomainResponseDto;
import vn.com.viettel.vds.vm2.pochibernateenvers.dto.response.HistoryResponseDto;
import vn.com.viettel.vds.vm2.pochibernateenvers.entity.Domain;
import vn.com.viettel.vds.vm2.pochibernateenvers.mapper.DomainMapper;
import vn.com.viettel.vds.vm2.pochibernateenvers.repository.DomainRepository;
import vn.com.viettel.vds.vm2.pochibernateenvers.service.DomainService;

@Service
@RequiredArgsConstructor
@Slf4j
public class DomainServiceImpl implements DomainService {

    private final DomainRepository domainRepository;
    private final DomainMapper domainMapper;
    private final SessionFactory sessionFactory;

    @Override
    public List<DomainResponseDto> findAll() {
        return new ArrayList<>(domainMapper.toDtos(domainRepository.findAll()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public DomainResponseDto save(DomainRequestDto request) {
        Domain savedDomain = domainMapper.toEntity(request);
        if (request.getId() != null) {
            savedDomain = domainRepository.findById(request.getId()).orElse(domainMapper.toEntity(request));
            savedDomain.setName(request.getName());
            savedDomain.setCategoryAmount(request.getCategoryAmount());
        }
        savedDomain = domainRepository.saveAndFlush(savedDomain);
        log.info("Transaction is flushed!");
        return domainMapper.toDto(savedDomain);
    }

    @Override
    public void delete(Long id) {
        domainRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<HistoryResponseDto> getHistory(Long domainId) {
        AuditReader auditReader = AuditReaderFactory.get(sessionFactory.getCurrentSession());
        List<Object[]> auditLogs = auditReader.createQuery()
            .forRevisionsOfEntity(Domain.class, true, true)
            .addProjection(AuditEntity.property("name"))
            .traverseRelation("categories", JoinType.INNER)
                .addProjection(AuditEntity.property("name"))
            .getResultList();
        return auditLogs.stream()
            .map(auditLog -> HistoryResponseDto.builder()
                .domainName((String) auditLog[0])
                .categoryNames((Set<String>) auditLog[1])
                .build())
            .collect(Collectors.toList());
    }
}
