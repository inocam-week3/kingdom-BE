package sparta.kingdombe.domain.job.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.domain.job.entity.JobInfo;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class JobAllResponseDto {
    private Long id;
    private String companyname;
    private String title;
    private String location;
    private Date recruitstartperiod;
    private Date recruitendperiod;
    private Long salary;
    private LocalDateTime createdAt;

    public JobAllResponseDto(JobInfo jobInfo) {
        this.id = jobInfo.getId();
        this.companyname = jobInfo.getCompanyname();
        this.title = jobInfo.getTitle();
        this.location = jobInfo.getLocation();
        this.recruitstartperiod = jobInfo.getRecruitmentStartPeriod();
        this.recruitendperiod = jobInfo.getRecruitmentEndPeriod();
        this.salary = jobInfo.getSalary();
        this.createdAt = jobInfo.getCreatedAt();
    }

}
