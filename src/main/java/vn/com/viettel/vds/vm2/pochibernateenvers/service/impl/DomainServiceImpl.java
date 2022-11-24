package vn.com.viettel.vds.vm2.pochibernateenvers.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.RevisionType;
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
import vn.com.viettel.vds.vm2.pochibernateenvers.entity.RevEntity;
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
        } else {
            domain = domainRepository.save(domain);
        }
        Set<Category> categories = domainMapper.toEntity(request).getCategories();
        if (categories != null && !categories.isEmpty()) {
            domain.addCategories(mergeCategories(categories));
        }
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
        Set<Category> mergedCategories = new HashSet<>(categories); // Use duplicate set to avoid ConcurrentModificationException
        for (Category category : categories) {
            if (category.getId() != null) {
                Category mergedCategory = categoryRepository.findById(category.getId()).orElse(category);
                mergedCategory.setName(category.getName());
                mergedCategories.remove(category); // Remove unmerged category
                mergedCategories.add(mergedCategory);
            }
        }
        return mergedCategories;
    }

    @Override
    public void delete(Long id) {
        domainRepository.deleteById(id);
    }

    @Override
    public List<HistoryResponseDto> getHistory(Long domainId) {
        List<Object[]> domainAuditLogs = auditReader.createQuery()
            .forRevisionsOfEntity(Domain.class, false, true)
            .add(AuditEntity.id().eq(domainId))
            .getResultList();
        List<Object[]> categoryAuditLogs = auditReader.createQuery()
            .forRevisionsOfEntity(Category.class, false, true)
            .add(AuditEntity.relatedId("domain").eq(domainId))
            .getResultList();
        List<HistoryResponseDto> historyResponseDtos = new ArrayList<>();
        for (Object[] domainAuditLog : domainAuditLogs) {
            HistoryResponseDto historyResponseDto = HistoryResponseDto.builder()
                .revision(((RevEntity) domainAuditLog[1]).getId())
                .revisionType((RevisionType) domainAuditLog[2])
                .domainName(((Domain) domainAuditLog[0]).getName())
                .build();
            Set<String> categoryNames = new HashSet<>();
            for (Object[] categoryAuditLog : categoryAuditLogs) {
                if (Objects.equals(categoryAuditLog[1], domainAuditLog[1])) {
                    categoryNames.add(((Category) categoryAuditLog[0]).getName());
                }
            }
            historyResponseDto.setCategoryNames(categoryNames);
            historyResponseDtos.add(historyResponseDto);
        }
        return historyResponseDtos;
    }
}
