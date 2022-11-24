package vn.com.viettel.vds.vm2.pochibernateenvers.dto.response;

import java.io.Serializable;
import java.util.Set;
import org.hibernate.envers.RevisionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class HistoryResponseDto implements Serializable {

    private Long revision;
    private RevisionType revisionType;
    private String domainName;
    private Set<String> categoryNames;
}
