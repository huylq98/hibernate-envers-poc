package vn.com.viettel.vds.vm2.pochibernateenvers.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vn.com.viettel.vds.vm2.pochibernateenvers.dto.request.DomainRequestDto;
import vn.com.viettel.vds.vm2.pochibernateenvers.dto.response.DomainResponseDto;
import vn.com.viettel.vds.vm2.pochibernateenvers.dto.response.HistoryResponseDto;
import vn.com.viettel.vds.vm2.pochibernateenvers.entity.Category;
import vn.com.viettel.vds.vm2.pochibernateenvers.entity.Domain;
import vn.com.viettel.vds.vm2.pochibernateenvers.mapper.DomainMapper;
import vn.com.viettel.vds.vm2.pochibernateenvers.repository.CategoryRepository;
import vn.com.viettel.vds.vm2.pochibernateenvers.repository.DomainRepository;
import vn.com.viettel.vds.vm2.pochibernateenvers.service.DomainService;

@Service
@RequiredArgsConstructor
@Slf4j
public class DomainServiceImpl implements DomainService {

    private final DomainRepository domainRepository;
    private final CategoryRepository categoryRepository;
    private final DomainMapper domainMapper;
    private final AuditReader auditReader;

    @Override
    public List<DomainResponseDto> findAll() {
        return new ArrayList<>(domainMapper.toDtos(domainRepository.findAll()));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public DomainResponseDto save(DomainRequestDto request) {
        Domain domain = domainMapper.toEntity(request);
        if (request.getId() != null) {
            domain = mergeDomain(domain);
        }
        domain.addCategories(mergeCategories(domainMapper.toEntity(request).getCategories()));
        domain = domainRepository.saveAndFlush(domain);
        log.info("Transaction is flushed!");
        // Throw exception here for rollback demonstration
        return domainMapper.toDto(domain);
    }

    private Domain mergeDomain(Domain domain) {
        Domain mergedDomain = domainRepository.findById(domain.getId()).orElse(domain);
        mergedDomain.setName(domain.getName());
        mergedDomain.setCategoryAmount(domain.getCategoryAmount());
        return mergedDomain;
    }

    private Set<Category> mergeCategories(Set<Category> categories) {
        Iterator<Category> categoryIterator = categories.iterator(); // Use iterator to avoid ConcurrentModificationException
        while (categoryIterator.hasNext()) {
            Category category = categoryIterator.next();
            if (category.getId() != null) {
                Category mergedCategory = categoryRepository.findById(category.getId()).orElse(category);
                mergedCategory.setName(category.getName());
                categories.remove(category); // Remove unmerged category
                categories.add(mergedCategory);
            }
        }
        return categories;
    }

    @Override
    public void delete(Long id) {
        domainRepository.deleteById(id);
    }

    @Override
    public List<HistoryResponseDto> getHistory(Long domainId) {
        List<Object[]> domainAuditLogs = auditReader.createQuery()
            .forRevisionsOfEntity(Domain.class, true, true)
            .add(AuditEntity.id().eq(domainId))
            .addProjection(AuditEntity.property("name"))
            .addProjection(AuditEntity.property("categoryAmount"))
            .getResultList();
        List<String> categoryAuditLogs = auditReader.createQuery()
            .forRevisionsOfEntity(Category.class, true, true)
            .add(AuditEntity.property("domain").eq(domainRepository.findById(domainId).orElseThrow(EntityNotFoundException::new)))
            .addProjection(AuditEntity.property("name"))
            .getResultList();
        return domainAuditLogs.stream()
            .map(domainAuditLog ->
                HistoryResponseDto.builder()
                    .domainName((String) domainAuditLog[0])
                    .categoryAmount((Integer) domainAuditLog[1])
                    .categoryNames(new HashSet<>(categoryAuditLogs))
                    .build()
            )
            .collect(Collectors.toList());
    }
}
