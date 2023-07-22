package sparta.kingdombe.domain.job.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class JobResponseDto {
    private Long id;
    private String companyname;
    private String title;
    private String location;
    private Date recuritPeriod;
    private Long salary;
    private LocalDateTime createAt;
}
