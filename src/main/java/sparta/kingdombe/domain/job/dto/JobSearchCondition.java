package sparta.kingdombe.domain.job.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JobSearchCondition {

    private String title;
    private String location;
    private Long salary;
    private Long recruitpersonnum;
}
