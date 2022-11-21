package vn.com.viettel.vds.vm2.pochibernateenvers.dto.response;

import java.io.Serializable;
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
public class DomainResponseDto implements Serializable {

    private Integer id;
    private Integer version;
    private String name;
}
