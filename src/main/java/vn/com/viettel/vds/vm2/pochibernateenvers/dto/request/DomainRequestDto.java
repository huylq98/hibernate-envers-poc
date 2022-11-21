package vn.com.viettel.vds.vm2.pochibernateenvers.dto.request;

import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DomainRequestDto implements Serializable {

    private Long id;
    private String name;
    private Integer categoryAmount;
    private Set<CategoryRequestDto> categories;
}
