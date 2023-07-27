package sparta.kingdombe.domain.job.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JobSearchCondition {

    private String title;
    private String location;
    private Long salary;
    private String recruitpersonnum;
}
