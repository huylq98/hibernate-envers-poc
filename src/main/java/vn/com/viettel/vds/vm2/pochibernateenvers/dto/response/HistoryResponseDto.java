package vn.com.viettel.vds.vm2.pochibernateenvers.dto.response;

import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class HistoryResponseDto implements Serializable {

    private String domainName;
    private Integer categoryAmount;
    private Set<String> categoryNames;
}
