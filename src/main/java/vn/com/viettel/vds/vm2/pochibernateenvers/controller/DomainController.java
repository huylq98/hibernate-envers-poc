package vn.com.viettel.vds.vm2.pochibernateenvers.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import vn.com.viettel.vds.vm2.pochibernateenvers.dto.request.DomainRequestDto;
import vn.com.viettel.vds.vm2.pochibernateenvers.dto.response.HistoryResponseDto;
import vn.com.viettel.vds.vm2.pochibernateenvers.dto.response.DomainResponseDto;
import vn.com.viettel.vds.vm2.pochibernateenvers.service.DomainService;

@RestController
@RequestMapping("/domains")
@RequiredArgsConstructor
public class DomainController {

    private final DomainService domainService;

    @GetMapping
    public List<DomainResponseDto> findAll() {
        return domainService.findAll();
    }

    @PostMapping
    public DomainResponseDto save(@RequestBody DomainRequestDto request) {
        return domainService.save(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        domainService.delete(id);
    }

    @GetMapping("/{id}/history")
    public List<HistoryResponseDto> getDomainHistory(@PathVariable("id") Long id) {
        return domainService.getHistory(id);
    }
}
