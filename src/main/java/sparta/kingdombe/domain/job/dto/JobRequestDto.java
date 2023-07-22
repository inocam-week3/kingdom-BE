package sparta.kingdombe.domain.job.dto;

import lombok.Getter;
import sparta.kingdombe.domain.job.entity.JobInfo;

import java.util.Date;

@Getter
public class JobRequestDto {

    String companyName;
    String title;
    String content;
    String location;
    Long salary;
    Date recruitmentPeriod;
    Long recruitmentPersonNum;
    String managerName;
    String managerEmail;


}
